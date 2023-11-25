package com.github.gunin_igor75.vk_application.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.usecases.ChangeLikesUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.DeletePostUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.LoadRecommendationsNextUseCase
import com.github.gunin_igor75.vk_application.domain.usecases.LoadRecommendationsUseCase
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    loadRecommendationsUseCase: LoadRecommendationsUseCase,
    private val loadRecommendationsNextUseCase: LoadRecommendationsNextUseCase,
    private val changeLikesUseCase: ChangeLikesUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d(TAG, "Exception caught exception handler")
    }


    private val recommendationFlow = loadRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val screenState = recommendationFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.PostState(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun nextLoadingNewsFeed() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.PostState(
                    posts = recommendationFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadRecommendationsNextUseCase()
        }
    }

    fun changeLikesStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikesUseCase(feedPost)
        }
    }

    fun deletePost(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }
}