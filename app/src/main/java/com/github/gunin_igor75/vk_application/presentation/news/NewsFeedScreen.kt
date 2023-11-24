package com.github.gunin_igor75.vk_application.presentation.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.presentation.news.NewsFeedScreenState.InitialState
import com.github.gunin_igor75.vk_application.ui.theme.DarkBlue


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsAndPostClickListener: (FeedPost) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel()

    val state = viewModel.screenState.collectAsState(InitialState)

    when (val currentState = state.value) {
        is NewsFeedScreenState.PostState -> {
            FeedPost(
                viewModel = viewModel,
                posts = currentState.posts,
                paddingValues = paddingValues,
                nextDataIsLoading = currentState.nextDataIsLoading,
                onCommentsAndPostClickListener = onCommentsAndPostClickListener
            )
        }
        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
        is InitialState -> {

        }
    }
}
