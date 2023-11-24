package com.github.gunin_igor75.vk_application.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginScreen
import com.github.gunin_igor75.vk_application.domain.entity.LoginState
import com.github.gunin_igor75.vk_application.domain.entity.LoginState.Initial
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vk_applicationTheme {
                val viewModel: MainViewModel = viewModel()
                val state = viewModel.stateAuth.collectAsState(Initial)
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult()
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



