package com.github.gunin_igor75.vk_application.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.VkRepositoryImp
import com.github.gunin_igor75.vk_application.domain.usecases.ChangeStateAuthUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.GetAuthorizationUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = VkRepositoryImp(application)

    private val changeStateAuthUseCase = ChangeStateAuthUseCase(repository)

    private val getAuthorizationUseCase = GetAuthorizationUseCase(repository)

    val stateAuth = changeStateAuthUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            getAuthorizationUseCase()
        }
    }
}