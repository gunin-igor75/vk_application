package com.github.gunin_igor75.vk_application.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.CommentsRepository
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsScreenState.InitialState
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val repository = CommentsRepository(application)

    private val _screenState = MutableLiveData<CommentsScreenState>(InitialState)

    var screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        _screenState.value = CommentsScreenState.Loading
        viewModelScope.launch {
            val comments = repository.loadComments(feedPost)
            _screenState.value = CommentsScreenState.CommentState(
                feedPost,
                comments
            )
        }
    }
}