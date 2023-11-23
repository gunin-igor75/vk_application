package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import android.util.Log
import com.github.gunin_igor75.vk_application.data.mapper.NewsFeedMapper
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.domain.StatisticType
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepository(
    application: Application
) : VkRepository(application) {

    private val mapper = NewsFeedMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null


    private val eventsNextLoading = MutableSharedFlow<Unit>(replay = 1)

    private val refreshFeedPost = MutableSharedFlow<List<FeedPost>>()

    private val loadRecommendations = flow {
        eventsNextLoading.emit(Unit)
        eventsNextLoading.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                Log.d(TAG, "Конец загрузки ${feedPosts.size}")
                return@collect
            }
            val data = if (startFrom == null) {
                apiService.loadNewsFeed(getTokenAccess())
            } else {
                apiService.loadNewsFeed(
                    token = getTokenAccess(),
                    startFrom = startFrom
                )
            }
            Log.d(TAG, "Загрузка при $startFrom")
//            Log.d(TAG, getTokenAccess())
            nextFrom = data.content.nextFrom
            _feedPosts.addAll(mapper.responseNewsFeedDtoToFeedPost(data))
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }

    val recommendation = loadRecommendations
        .mergeWith(refreshFeedPost)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )


    suspend fun changeLikes(feedPost: FeedPost) {
        val likes = responseLikesCount(feedPost)
        val index = _feedPosts.indexOf(feedPost)
        val statisticsNew = feedPost.statistics.toMutableList()
        statisticsNew.apply {
            removeIf { it.type == StatisticType.LIKES }
            add(
                StatisticItem(
                    count = likes.likesCount.count,
                    type = StatisticType.LIKES
                )
            )
        }
        _feedPosts[index] = feedPost.copy(
            statistics = statisticsNew,
            isUserLikes = !feedPost.isUserLikes
        )
        refreshFeedPost.emit(feedPosts)
    }

    suspend fun loadDataNext() {
        eventsNextLoading.emit(Unit)
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getTokenAccess(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshFeedPost.emit(feedPosts)
    }

    private suspend fun responseLikesCount(feedPost: FeedPost) =
        if (!feedPost.isUserLikes) {
            apiService.addLikes(
                token = getTokenAccess(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.deleteLikes(
                token = getTokenAccess(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

    private fun getTokenAccess(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    companion object {
        private const val RETRY_TIMEOUT = 3000L
        private const val TAG = "NewsFeedRepository"
    }
}
