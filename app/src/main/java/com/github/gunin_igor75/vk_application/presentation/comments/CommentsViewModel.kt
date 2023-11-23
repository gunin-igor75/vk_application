package com.github.gunin_igor75.vk_application.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.CommentsRepository
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val repository = CommentsRepository(application)

    private val loadNextDataFlow = MutableSharedFlow<CommentsScreenState>()

    private val flowTemp = repository.loadComments(feedPost = feedPost)


    val commentsFlow = flowTemp
        .filter { it.isNotEmpty() }
        .map {CommentsScreenState.CommentState(
                feedPost = feedPost,
                comments = it,
                nextDataLoading = false
            ) as CommentsScreenState
        }
        .onStart { emit(CommentsScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun nextLoadingComments() {
        viewModelScope.launch {
            repository.loadNextComments()
        }
    }


    companion object {
        private const val TAG = "CommentsViewModel"
    }
}