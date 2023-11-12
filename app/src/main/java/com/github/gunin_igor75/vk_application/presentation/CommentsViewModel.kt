package com.github.gunin_igor75.vk_application.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.vk_application.domain.Comment
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.ui.theme.comments.CommentsScreenState
import com.github.gunin_igor75.vk_application.ui.theme.comments.CommentsScreenState.InitialState

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(InitialState)
    var screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }


    private fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<Comment>().apply {
            repeat(10) {
                add(Comment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.CommentState(
            feedPost = feedPost,
            comments = comments
        )
    }
}