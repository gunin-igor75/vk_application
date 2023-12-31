package com.github.gunin_igor75.vk_application.presentation.comments

import com.github.gunin_igor75.vk_application.domain.entity.Comment
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost

sealed class CommentsScreenState {
    object InitialState : CommentsScreenState()

    object Loading: CommentsScreenState()

    data class CommentState(
        val feedPost: FeedPost,
        val comments: List<Comment>,
        val nextDataLoading: Boolean = false
    ) : CommentsScreenState()
}
