package com.github.gunin_igor75.vk_application.di

import android.app.Application
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.github.gunin_igor75.vk_application.data.network.ApiService
import com.github.gunin_igor75.vk_application.data.repository.VkRepositoryImp
import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindsVkRepository(impl: VkRepositoryImp): VkRepository


    companion object {
        @ApplicationScope
        @Provides
        fun provideAppService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVKPreferencesKeyValueStorage(application: Application): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(application)
        }
    }
}