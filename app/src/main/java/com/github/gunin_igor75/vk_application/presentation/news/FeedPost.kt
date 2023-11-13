package com.github.gunin_igor75.vk_application.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.vk_application.domain.FeedPost

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPost(
    viewModel: NewsFeedViewModel,
    posts: List<FeedPost>,
    paddingValues: PaddingValues,
    onCommentsAndPostClickListener: (FeedPost) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { feedPost ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deletePost(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                    ) {
                    }
                }
            ) {
                CardPost(
                    feedPost = feedPost,
                    onViewAndPostClickListener = { statisticItem ->
                        viewModel.changeCountStatistic(statisticItem, feedPost)
                    },
                    onSharedAndPostClickListener = { statisticItem ->
                        viewModel.changeCountStatistic(statisticItem, feedPost)
                    },
                    onCommentsAndPostClickListener = {
                        onCommentsAndPostClickListener(feedPost)
                    },
                    onLakesAndPostClickListener = { statisticItem ->
                        viewModel.changeCountStatistic(statisticItem, feedPost)
                    }
                )
            }
        }
    }
}