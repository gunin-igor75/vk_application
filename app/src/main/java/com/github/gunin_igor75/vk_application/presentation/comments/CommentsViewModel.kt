package com.github.gunin_igor75.vk_application.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.usecases.LoadCommentsUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.LoadNextCommentsUseCase
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    loadCommentsUseCase: LoadCommentsUseCase,
    private val loadNextCommentsUseCase: LoadNextCommentsUseCase,
) : ViewModel() {

    private val loadNextDataFlow = MutableSharedFlow<CommentsScreenState>()

    private val stateFlowComments = loadCommentsUseCase(feedPost = feedPost)

    val commentsFlow = stateFlowComments
        .filter { it.isNotEmpty() }
        .map {
            CommentsScreenState.CommentState(
                feedPost = feedPost,
                comments = it,
                nextDataLoading = false
            ) as CommentsScreenState
        }
        .onStart { emit(CommentsScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun nextLoadingComments() {
        viewModelScope.launch {
            loadNextCommentsUseCase()
        }
    }
}