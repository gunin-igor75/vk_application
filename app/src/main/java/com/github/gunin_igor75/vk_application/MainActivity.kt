package com.github.gunin_igor75.vk_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.vk_application.ui.theme.CardPost
import com.github.gunin_igor75.vk_application.ui.theme.InstagramCard
import com.github.gunin_igor75.vk_application.ui.theme.MainScreen
import com.github.gunin_igor75.vk_application.ui.theme.MainViewModel
import com.github.gunin_igor75.vk_application.ui.theme.MenuScaffold
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            Vk_applicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InstagramCard(viewModel)
                }
            }
        }
    }
}

