package com.github.gunin_igor75.vk_application.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.domain.usecases.ChangeStateAuthUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.GetAuthorizationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    changeStateAuthUseCase: ChangeStateAuthUseCase,
    private val getAuthorizationUseCase: GetAuthorizationUseCase,
) : ViewModel() {


    val stateAuth = changeStateAuthUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            getAuthorizationUseCase()
        }
    }
}