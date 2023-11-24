package com.github.gunin_igor75.vk_application.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.VkRepositoryImp
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

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VkRepositoryImp(application)
    
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d(TAG, "Exception caught exception handler")
    }

    private val loadRecommendationsUseCase = LoadRecommendationsUseCase(repository)

    private val loadRecommendationsNextUseCase = LoadRecommendationsNextUseCase(repository)

    private val changeLikesUseCase = ChangeLikesUseCase(repository)

    private val deletePostUseCase = DeletePostUseCase(repository)

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