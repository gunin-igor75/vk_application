package com.github.gunin_igor75.vk_application.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.repository.NewsFeedRepository
import com.github.gunin_igor75.vk_application.domain.FeedPost
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private var _screenState =
        MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.InitialState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadNewsFeed()
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.PostState(
                posts = repository.loadRecommendations()
            )
        }
    }

    fun nextLoadingNewsFeed() {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.PostState(
                posts = repository.loadRecommendations(),
                nextDataIsLoading = true
            )
        }
    }

    fun changeLikesStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikes(feedPost)
            _screenState.value = NewsFeedScreenState.PostState(
                posts = repository.feedPosts
            )
        }
    }

    fun deletePost(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
            _screenState.value =NewsFeedScreenState.PostState(posts = repository.feedPosts)
        }
    }
}