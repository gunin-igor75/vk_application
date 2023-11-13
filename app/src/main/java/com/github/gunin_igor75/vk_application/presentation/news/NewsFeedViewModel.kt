package com.github.gunin_igor75.vk_application.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.mapper.NewsFeedMapper
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()

    private var _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.InitialState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState

    init {
        loadNewsFeed()
    }

    fun changeCountStatistic(item: StatisticItem, post: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.PostState)  return

        val posts = currentState.posts.toMutableList()
        posts.apply {
            replaceAll {feedPost ->
                if (feedPost.id == post.id) {
                    val oldStatics = feedPost.statistics.toMutableList().apply {
                        replaceAll { newItem ->
                            if (newItem == item) {
                                newItem.copy(count = newItem.count + 1)
                            } else {
                                newItem
                            }
                        }
                    }
                    feedPost.copy(statistics = oldStatics)
                } else {
                    feedPost
                }
            }
        }
        _screenState.value = currentState.copy(posts =posts)
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val data = apiService.loadNewsFeed(token.accessToken)
            val newsFeed = mapper.responseNewsFeedDtoToFeedPost(data)
            _screenState.value = NewsFeedScreenState.PostState(newsFeed)
        }

    }

    fun deletePost(post: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.PostState)  return

        val postsNew = currentState.posts.toMutableList()
        postsNew.remove(post)
        _screenState.value = currentState.copy(posts =  postsNew)
    }
}