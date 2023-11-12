package com.github.gunin_igor75.vk_application.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.github.gunin_igor75.vk_application.ui.theme.home.MainScreen
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vk_applicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Run()
                }
            }
        }
    }

    @Composable
    fun Run() {
        MainScreen()
    }
}



