package com.github.gunin_igor75.vk_application.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.gunin_igor75.vk_application.di.ViewModelFactory
import com.github.gunin_igor75.vk_application.domain.entity.NavComponent.Favorite
import com.github.gunin_igor75.vk_application.domain.entity.NavComponent.Home
import com.github.gunin_igor75.vk_application.domain.entity.NavComponent.Profile
import com.github.gunin_igor75.vk_application.navigation.AppNavGraph
import com.github.gunin_igor75.vk_application.navigation.rememberNavigationState
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsScreen
import com.github.gunin_igor75.vk_application.presentation.news.HomeScreen


@Composable
fun MainScreen(
    viewModelFactory: ViewModelFactory
) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStateEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val list = listOf(Home, Favorite, Profile)
                list.forEach { item ->
                    val selected = navBackStateEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
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
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(
                    paddingValues = paddingValues,
                    onCommentsAndPostClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    viewModelFactory
                )
            },
            commentsScreenContent = {feedPost ->
                CommentsScreen(
                    feedPost = feedPost
                ) {
                    navigationState.navHostController.popBackStack()
                }
                BackHandler {
                    navigationState.navHostController.popBackStack()
                }
            },
            favoriteScreenContent = {
                TextCounter(name = "Favorite")
            },
            profileScreenContent = {
                TextCounter(name = "Profile")
            }
        )
    }
}

@Composable
private fun TextCounter(name: String) {

    var count by rememberSaveable {
        mutableIntStateOf(0)
    }

    Text(
        text = "$name Cont $count",
        modifier = Modifier.clickable {
            count++
        }
    )
}


