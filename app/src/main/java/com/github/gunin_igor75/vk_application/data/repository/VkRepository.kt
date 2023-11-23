package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

abstract class VkRepository(
    application: Application
) {
    private val storage = VKPreferencesKeyValueStorage(application)
    protected val token
        get() =  VKAccessToken.restore(storage)

    protected val apiService = ApiFactory.apiService
}