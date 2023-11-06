package com.github.gunin_igor75.vk_application.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private var _isFollowed = MutableLiveData<Boolean>(false)
    val isFollowed: LiveData<Boolean> = _isFollowed

    fun inversionFollowed() {
        val isFollowed = _isFollowed.value ?: false
        _isFollowed.value = !isFollowed
    }
}