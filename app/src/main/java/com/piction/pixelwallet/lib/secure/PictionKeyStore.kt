package com.piction.pixelwallet.lib.secure

import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference
import org.spongycastle.asn1.x500.X500Name
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo
import org.spongycastle.cert.X509v3CertificateBuilder
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.math.BigInteger
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*

import android.security.keystore.KeyProtection
import org.spongycastle.asn1.ASN1ObjectIdentifier
import org.spongycastle.asn1.x509.AlgorithmIdentifier
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletUtils


class PictionKeyStore(
    private val cipherHelper: CipherHelper,
    private val dynamicPreference: DynamicPreference
) {

    private val pictionAlias = "com.piction.pixelwallet"

    private val androidKeyStore by lazy { KeyStore.getInstance("AndroidKeyStore").apply { load(null) } }

    fun putKeyPair(alias: String, keyPair: KeyPair) {

        if (dynamicPreference.get(pictionAlias, "").isEmpty()) {
            dynamicPreference.put(pictionAlias, cipherHelper.encrypt(generateRandom()))
        }

        try {

            Timber.d(keyPair.private.algorithm)
            Timber.d(keyPair.public.algorithm)

            val cert = generateCertificate(keyPair) as Certificate
            //todo keyPair public algorithm not match cert publicKey algorithm
            Timber.d(cert.publicKey.algorithm)

            androidKeyStore.setEntry(
                alias,
                KeyStore.PrivateKeyEntry(
                    keyPair.private,
                    arrayOfNulls<Certificate>(1).apply { this[0] = generateCertificate(keyPair) as Certificate }),
                    KeyStore.PasswordProtection("soso".toCharArray()) //todo use dynamicPreference.get(pictionAlias, "")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

    fun getKeyPair(alias: String): KeyPair {
        val key = androidKeyStore.getEntry(
            alias,
            KeyStore.PasswordProtection("soso".toCharArray()) //todo use dynamicPreference.get(pictionAlias, "")
        )
        val cert = androidKeyStore.getCertificate(alias)
        val publicKey = cert.publicKey

        return KeyPair(publicKey, (key as KeyStore.PrivateKeyEntry).privateKey)
    }

    fun removeKeyStore(alias: String) {
        androidKeyStore.deleteEntry(alias)
    }

    fun getAliasList(): List<String> = androidKeyStore.aliases().toList()

    private fun generateRandom(): String {
        return try {
            val secureRandom = SecureRandom.getInstance("SHA1PRNG")
            val digest = MessageDigest.getInstance("SHA-256")

            secureRandom.setSeed(secureRandom.generateSeed(128))
            digest.digest(secureRandom.nextLong().toString().toByteArray()).toString()
        } catch (e: NoSuchAlgorithmException) {
            throw e
        }
    }

    private fun generateCertificate(keyPair: KeyPair): X509Certificate = X509v3CertificateBuilder(
        X500Name("CN=pixelwallet, O=Piction Network"),
        BigInteger.valueOf(1),
        Calendar.getInstance(Locale.ENGLISH).time,
        Calendar.getInstance(Locale.ENGLISH).apply { set(9999, 12, 31) }.time,
        X500Name("CN=pixelwallet, O=Piction Network"),
        SubjectPublicKeyInfo.getInstance(keyPair.public.encoded)
    ).run {
        val builder = JcaContentSignerBuilder("SHA256withECDSA")
        val signer = builder.build(keyPair.private)
        val certBytes = this.build(signer).encoded
        val certificateFactory = CertificateFactory.getInstance("X.509")
        certificateFactory.generateCertificate(ByteArrayInputStream(certBytes)) as X509Certificate
    }
}