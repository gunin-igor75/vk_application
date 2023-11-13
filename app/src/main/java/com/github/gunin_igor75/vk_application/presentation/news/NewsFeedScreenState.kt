package com.github.gunin_igor75.vk_application.presentation.news

import com.github.gunin_igor75.vk_application.domain.FeedPost

sealed class NewsFeedScreenState {
    object InitialState : NewsFeedScreenState()
    data class PostState(
        val posts: List<FeedPost>
    ) : NewsFeedScreenState()
}
