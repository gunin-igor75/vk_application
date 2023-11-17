package com.github.gunin_igor75.vk_application.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.NewsFeedRepository
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val recommendationFlow = repository.recommendation

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
            repository.loadDataNext()
        }
    }

    fun changeLikesStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikes(feedPost)
        }
    }

    fun deletePost(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
        }
    }
}