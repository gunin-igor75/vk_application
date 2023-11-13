package com.github.gunin_igor75.vk_application.presentation.main.login

sealed class LoginState{

    object Initial: LoginState()
    object Authorized: LoginState()
    object NoAuthorized: LoginState()
}
