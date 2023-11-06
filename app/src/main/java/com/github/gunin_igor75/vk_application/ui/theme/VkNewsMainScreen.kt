package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.gunin_igor75.vk_application.ui.theme.NavBottomComponent.*
import kotlinx.coroutines.launch


@Preview
@Composable
fun MainScreen() {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember {
        mutableStateOf(true)
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            if (fabIsVisible.value) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val action = snackBarHostState.showSnackbar(
                                message = "This si SnackBar",
                                actionLabel = "Hide FAB",
                                duration = SnackbarDuration.Long
                            )
                            if (action == SnackbarResult.ActionPerformed) {
                                fabIsVisible.value = false
                            }
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigation {
                val selectedPosition = remember {
                    mutableStateOf(0)
                }
                val list = listOf(Home, Favorite, Profile)

                list.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedPosition.value == index,
                        onClick = {
                            selectedPosition.value = index
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.labelId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) {
        { it }
    }
}

