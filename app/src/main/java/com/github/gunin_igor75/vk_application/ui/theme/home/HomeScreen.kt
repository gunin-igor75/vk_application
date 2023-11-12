package com.github.gunin_igor75.vk_application.ui.theme.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.presentation.NewsFeedViewModel
import com.github.gunin_igor75.vk_application.ui.theme.home.NewsFeedScreenState.InitialState


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsAndPostClickListener: (FeedPost) -> Unit
) {
    val viewModel:NewsFeedViewModel = viewModel()

    val state = viewModel.screenState.observeAsState(InitialState)

    when (val currentState = state.value) {
        is NewsFeedScreenState.PostState -> {
            FeedPost(
                viewModel = viewModel,
                posts = currentState.posts,
                paddingValues = paddingValues,
                onCommentsAndPostClickListener = onCommentsAndPostClickListener
            )
        }
        is InitialState -> {

        }
    }
}
