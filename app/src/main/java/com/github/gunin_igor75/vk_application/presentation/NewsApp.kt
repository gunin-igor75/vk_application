package com.github.gunin_igor75.vk_application.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.github.gunin_igor75.vk_application.di.ApplicationComponent
import com.github.gunin_igor75.vk_application.di.DaggerApplicationComponent

class NewsApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }



}
@Composable
fun getComponentNewsApp(): ApplicationComponent {
    return (LocalContext.current.applicationContext as NewsApp).component
}