package com.github.gunin_igor75.vk_application.presentation

import android.app.Application
import com.github.gunin_igor75.vk_application.di.DaggerApplicationComponent

class NewsApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}