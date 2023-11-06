package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.gunin_igor75.vk_application.R

sealed class NavBottomComponent(
    val labelId: Int,
    val icon: ImageVector
) {
    object Home : NavBottomComponent(
        labelId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavBottomComponent(
        labelId = R.string.navigation_item_favorite,
        icon = Icons.Filled.Favorite
    )

    object Profile : NavBottomComponent(
        labelId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}