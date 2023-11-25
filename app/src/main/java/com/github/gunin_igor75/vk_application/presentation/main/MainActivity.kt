package com.github.gunin_igor75.vk_application.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.di.ViewModelFactory
import com.github.gunin_igor75.vk_application.presentation.main.login.LoginScreen
import com.github.gunin_igor75.vk_application.domain.entity.LoginState
import com.github.gunin_igor75.vk_application.domain.entity.LoginState.Initial
import com.github.gunin_igor75.vk_application.presentation.NewsApp
import com.github.gunin_igor75.vk_application.ui.theme.Vk_applicationTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as NewsApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            Vk_applicationTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val state = viewModel.stateAuth.collectAsState(Initial)
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult()
                }
                when (state.value) {
                    LoginState.Authorized -> {
                        MainScreen(
                            viewModelFactory
                        )
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



