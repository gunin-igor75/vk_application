package com.github.gunin_igor75.vk_application.domain.entity

sealed class LoginState{

    object Initial: LoginState()
    object Authorized: LoginState()
    object NoAuthorized: LoginState()
}
