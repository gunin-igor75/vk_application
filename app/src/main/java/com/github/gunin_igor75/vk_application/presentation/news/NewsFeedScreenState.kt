package com.github.gunin_igor75.vk_application.presentation.news

import com.github.gunin_igor75.vk_application.domain.entity.FeedPost

sealed class NewsFeedScreenState {
    object InitialState : NewsFeedScreenState()

    object Loading: NewsFeedScreenState()
    data class PostState(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()
}
