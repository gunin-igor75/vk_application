package com.github.gunin_igor75.vk_application.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.gunin_igor75.vk_application.R
import com.github.gunin_igor75.vk_application.navigation.Screen

sealed class NavComponent(
    val screen: Screen,
    val labelId: Int,
    val icon: ImageVector
) {
    object Home : NavComponent(
        screen = Screen.Home,
        labelId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavComponent(
        screen = Screen.Favorite,
        labelId = R.string.navigation_item_favorite,
        icon = Icons.Filled.Favorite
    )

    object Profile : NavComponent(
        screen = Screen.Profile,
        labelId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}