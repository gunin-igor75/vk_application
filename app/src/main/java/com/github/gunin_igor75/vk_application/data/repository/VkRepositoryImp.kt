package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import com.github.gunin_igor75.vk_application.data.mapper.VkMapper
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.github.gunin_igor75.vk_application.domain.entity.Comment
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.entity.LoginState
import com.github.gunin_igor75.vk_application.domain.entity.StatisticItem
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType
import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import com.github.gunin_igor75.vk_application.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class VkRepositoryImp(
    application: Application
) : VkRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val mapper = VkMapper()

    private val apiService = ApiFactory.apiService

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val eventStateAuth = MutableSharedFlow<Unit>(replay = 1)

    private val eventsNextLoading = MutableSharedFlow<Unit>(replay = 1)

    private val refreshFeedPost = MutableSharedFlow<List<FeedPost>>()

    private val _comments = mutableListOf<Comment>()

    private var startId: Long? = null
    private val comments: List<Comment>
        get() = _comments.toList()

    private val eventFlow = MutableSharedFlow<Unit>(replay = 1)


    private val loadRecommendations = flow {
        eventsNextLoading.emit(Unit)
        eventsNextLoading.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
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
            nextFrom = data.content.nextFrom
            _feedPosts.addAll(mapper.responseNewsFeedDtoToFeedPost(data))
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }

    private val recommendation = loadRecommendations
        .mergeWith(refreshFeedPost)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    private val stateAuth = flow {
        eventStateAuth.emit(Unit)
        eventStateAuth.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val state = if (loggedIn) LoginState.Authorized else LoginState.NoAuthorized
            emit(state)
        }
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = LoginState.Initial
    )

    override fun loadRecommendations(): StateFlow<List<FeedPost>> {
        return recommendation
    }

    override suspend fun loadRecommendationsNext() {
        eventsNextLoading.emit(Unit)
    }

    override suspend fun changeLikes(feedPost: FeedPost) {
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

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getTokenAccess(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshFeedPost.emit(feedPosts)
    }

    override fun loadComments(feedPost: FeedPost) = flow {
        eventFlow.emit(Unit)
        eventFlow.collect {
            val currentStartId = startId
            if (startId == null && comments.isNotEmpty()) {
                emit(comments)
                return@collect
            }

            val response = if (currentStartId == null) {
                apiService.getComments(
                    token = getTokenAccess(),
                    ownerId = feedPost.communityId,
                    postId = feedPost.id
                )
            } else {
                apiService.getComments(
                    token = getTokenAccess(),
                    ownerId = feedPost.communityId,
                    postId = feedPost.id,
                    startCommentId = currentStartId
                )
            }
            val commentsResponse = mapper.createComments(response)
            startId =
                if (commentsResponse.isNotEmpty()) commentsResponse[commentsResponse.size - 1].id else null
            _comments.addAll(commentsResponse)
            emit(comments)
        }
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun loadNextComments() {
        eventFlow.emit(Unit)
    }

    override fun changeStateAuth(): StateFlow<LoginState> {
        return stateAuth
    }

    override suspend fun getAuthorization() {
        eventStateAuth.emit(Unit)
    }

    private fun getTokenAccess(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
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

    companion object {
        private const val RETRY_TIMEOUT = 3000L
    }
}