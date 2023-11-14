package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import com.github.gunin_igor75.vk_application.data.mapper.NewsFeedMapper
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(
    application: Application
) {

    private val storage = VKPreferencesKeyValueStorage(application)

    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()

    private var nextFom: String? = null

    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom = nextFom
        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts
        val data = if (startFrom == null) {
            apiService.loadNewsFeed(getTokenAccess())
        } else {
            apiService.loadNewsFeed(getTokenAccess(), startFrom)
        }
        nextFom = data.content.nextFrom
        _feedPosts.addAll(mapper.responseNewsFeedDtoToFeedPost(data))
        return feedPosts
    }

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
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getTokenAccess(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
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
}
