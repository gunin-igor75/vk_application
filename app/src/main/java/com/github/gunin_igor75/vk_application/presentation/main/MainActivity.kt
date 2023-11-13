package com.github.gunin_igor75.vk_application.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginScreen
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginState
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginState.Initial
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vk_applicationTheme {
                val viewModel: MainViewModel = viewModel()
                val state = viewModel.stateLoin.observeAsState(Initial)
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult(it)
                }
                when (state.value) {
                    LoginState.Authorized -> {
                        MainScreen()
                    }

                    LoginState.NoAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }
                    Initial -> {}
                }
            }
        }
    }
}



