package com.github.gunin_igor75.vk_application.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.github.gunin_igor75.vk_application.domain.FeedPost


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {
            val feedPost = getFeedPost(it)
            commentsScreenContent(feedPost)
        }
    }
}
private fun getFeedPost(it: NavBackStackEntry) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        it.arguments?.getParcelable(
            Screen.KEY_FEED_POST, FeedPost::class.java
        ) ?: throw IllegalArgumentException("Args is null")
    } else {
        it.arguments?.getParcelable(Screen.KEY_FEED_POST) ?: throw IllegalArgumentException("Args is null")
    }