package com.github.gunin_igor75.vk_application.ui.theme.home

import com.github.gunin_igor75.vk_application.domain.FeedPost

sealed class NewsFeedScreenState {
    object InitialState : NewsFeedScreenState()
    data class PostState(
        val posts: List<FeedPost>
    ) : NewsFeedScreenState()
}
