package com.github.gunin_igor75.vk_application.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginState
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var _stateLogin = MutableLiveData<LoginState>(LoginState.Initial)
    val stateLoin = _stateLogin

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        Log.d(TAG, "${token?.accessToken}")
        _stateLogin.value = if (loggedIn) LoginState.Authorized else LoginState.NoAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _stateLogin.value = LoginState.Authorized
        } else {
            _stateLogin.value = LoginState.NoAuthorized
            val exception = (result as VKAuthenticationResult.Failed).exception
            Log.d(TAG, "${exception.message}")
        }
    }

    companion object{
        @JvmStatic private val TAG = "MainViewModel"
    }
}