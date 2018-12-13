package com.piction.pixelwallet.ui.view.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CreateAccountActivityViewModel @Inject
constructor(
) : ViewModel() {

    private val createAccountLiveData: MutableLiveData<String> = MutableLiveData()
    val createAccount: LiveData<String> get() = createAccountLiveData

    private val startActivityLiveData: MutableLiveData<String> = MutableLiveData()
    val startActivity: LiveData<String> get() = startActivityLiveData

    fun createAccount(string: String) {
        //todo observable
        //user.createAccount(string)
    }

    fun startActivity(msg: String) {
        startActivityLiveData.postValue(msg)
    }
}