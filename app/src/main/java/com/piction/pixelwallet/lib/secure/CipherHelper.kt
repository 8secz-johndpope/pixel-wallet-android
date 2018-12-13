package com.piction.pixelwallet.lib.secure

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import org.spongycastle.crypto.params.ECKeyGenerationParameters
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import timber.log.Timber
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec.F4
import javax.crypto.Cipher


class CipherHelper(applicationContext: Context) {

    private val keyProviderName = "AndroidKeyStore"

    private val cipherAlgorithm = "${KeyProperties.KEY_ALGORITHM_RSA}/" +
            "${KeyProperties.BLOCK_MODE_ECB}/" +
            KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1

    private val alias = "${applicationContext.packageName}.rsakeypairs"

    private var keyEntry: KeyStore.Entry

    private val keyStore: KeyStore = KeyStore.getInstance(keyProviderName).apply {
        load(null)
    }

    private var _isSupported = false

    val isSupported: Boolean
        get() = _isSupported

    init {
        _isSupported = if (keyStore.containsAlias(alias)) {
            true
        } else {
            Timber.v("No keypair for $alias, creating a new one")
            Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && initAndroidM(alias)

            //todo if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && initAndroidM(pwdAlias)){true}
            //todo else { //todo initAndroidL(pwdAlias)}
        }

        keyEntry = keyStore.getEntry(alias, null)

        Security.insertProviderAt(org.spongycastle.jce.provider.BouncyCastleProvider(), 1)
    }

    private fun initAndroidM(alias: String): Boolean {
        try {
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, keyProviderName).run {
                val spec = KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(2048, F4))
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setDigests(
                        KeyProperties.DIGEST_SHA512,
                        KeyProperties.DIGEST_SHA384,
                        KeyProperties.DIGEST_SHA256
                    )
                    /*
                     * Setting true only permit the private key to be used if the user authenticated
                     * within the last five minutes.
                     */
                    .setUserAuthenticationRequired(false)
                    .build()
                initialize(spec)
                generateKeyPair()
            }
            Timber.i("Random keypair with ${KeyProperties.KEY_ALGORITHM_RSA}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1} is created.")

            return true
        } catch (e: GeneralSecurityException) {
            /*
             * Nonsense, but some devices manufactured by developing countries have actual problem
             * Consider using JCE substitutes like Spongy castle(Bouncy castle for android)
             */
            Timber.w(e, "It seems that this device does not support latest algorithm!!")

            return false
        }
    }

    fun encrypt(plainText: String): String {
        if (!_isSupported) {
            return plainText
        }

        val cipher = Cipher.getInstance(cipherAlgorithm).apply {
            init(Cipher.ENCRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).certificate.publicKey)
        }
        val bytes = plainText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = cipher.doFinal(bytes)
        val base64EncryptedBytes = Base64.encode(encryptedBytes, Base64.DEFAULT)

        return String(base64EncryptedBytes)
    }

    fun decrypt(base64EncryptedCipherText: String): String {
        if (!_isSupported) {
            return base64EncryptedCipherText
        }

        val cipher = Cipher.getInstance(cipherAlgorithm).apply {
            init(Cipher.DECRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).privateKey)
        }
        val base64EncryptedBytes = base64EncryptedCipherText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = Base64.decode(base64EncryptedBytes, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes)
    }

    fun createAddr() {

        val pwd = "soso"
        val alias = "key1"

        val keyStore = KeyStore.getInstance(keyProviderName).apply { load(null) }

        //저장된 목록 출력
        val aliases = keyStore.aliases()
        Log.d("web3", "list: " + aliases.toList().toString())

        //저장되어 있는지 확인
        if (keyStore.containsAlias(alias)) {
            val keyEntry: KeyStore.Entry = keyStore.getEntry(alias, null)
            (keyEntry as KeyStore.PrivateKeyEntry).privateKey
            Log.d("web3", "containsAlias private key:" + keyEntry.privateKey)
        }

        //web3j 계정 생성
        var ecKeyPair = Keys.createEcKeyPair()
        Log.d("web3", "private key:" + ecKeyPair.privateKey)
        Log.d("web3", "public key:" + ecKeyPair.publicKey)

        //자 이제 저장해보자
        //파일 방법 1
        val wallet = Wallet.createLight(pwd, ecKeyPair)
        wallet.address

        // 등등
        // WalletUtils.generateWalletFile()


        //android CipherHelper 방법
        //keypair 생성
        //refer Keys.createEcKeyPair()

        val keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "SC")
        val ecGenParameterSpec = ECGenParameterSpec("secp256k1")
        keyPairGenerator.initialize(ecGenParameterSpec, SecureRandom())
        val keyPair = keyPairGenerator.genKeyPair()

        Log.d("web3", "private key:" + keyPair.private)
        Log.d("web3", "public key:" + keyPair.public)

        //저장 쉬발 것 ...
        //keyStore.setEntry(alias, it., null)

        //저장된 것 가져와서 출력
        ecKeyPair = ECKeyPair.create(getKeyPair(keyStore, alias, pwd))

        Log.d("web3", "private key:" + ecKeyPair.privateKey)
        Log.d("web3", "public key:" + ecKeyPair.publicKey)
        Log.d("web3", "getAddress:" + Keys.getAddress(ecKeyPair))

        //웰렛 생성
        //Wallet.createLight(pwd, ecKeyPair)

        //웰렛 파일 output
        //WalletUtils.generateWalletFile(pwd, ecKeyPair, File("didodo"), false)
    }

    fun getKeyPair(keystore: KeyStore, alias: String, password: String): KeyPair {
        val key = keystore.getKey(alias, password.toCharArray()) as PrivateKey

        val cert = keystore.getCertificate(alias)
        val publicKey = cert.publicKey

        return KeyPair(publicKey, key)
    }
}