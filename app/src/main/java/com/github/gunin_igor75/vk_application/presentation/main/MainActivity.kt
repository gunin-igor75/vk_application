package com.github.gunin_igor75.vk_application.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.domain.entity.LoginState
import com.github.gunin_igor75.vk_application.domain.entity.LoginState.Initial
import com.github.gunin_igor75.vk_application.presentation.getComponentNewsApp
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginScreen
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getComponentNewsApp()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val state = viewModel.stateAuth.collectAsState(Initial)
            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                viewModel.performAuthResult()
            }
            Vk_applicationTheme {
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



