package com.github.gunin_igor75.vk_application.presentation.comments

import com.github.gunin_igor75.vk_application.domain.Comment
import com.github.gunin_igor75.vk_application.domain.FeedPost

sealed class CommentsScreenState {
    object InitialState : CommentsScreenState()

    data class CommentState(
        val feedPost: FeedPost,
        val comments: List<Comment>
    ) : CommentsScreenState()
}
