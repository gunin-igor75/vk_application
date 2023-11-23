package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.vk_application.data.dto.comments.ResponseCommentsDto
import com.github.gunin_igor75.vk_application.data.mapper.CommentsMapper
import com.github.gunin_igor75.vk_application.domain.Comment
import com.github.gunin_igor75.vk_application.domain.FeedPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class CommentsRepository(
    application: Application
) : VkRepository(application) {

    private val mapper = CommentsMapper()

    private val _comments = mutableListOf<Comment>()

    private val scopeCor = CoroutineScope(Dispatchers.Default)

    private var startId: Long? = null
    private val comments: List<Comment>
        get() = _comments.toList()

    private val eventFlow = MutableSharedFlow<Unit>(replay = 1)

    fun loadComments(feedPost: FeedPost) = flow {
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
            startId = if (commentsResponse.isNotEmpty()) commentsResponse[commentsResponse.size -1].id else null
            _comments.addAll(commentsResponse)
            emit(comments)
        }
    }.stateIn(
        scope = scopeCor,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    suspend fun loadNextComments() {
        eventFlow.emit(Unit)
    }

    private fun getTokenAccess(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    companion object {
        private const val RETRY_TIMEOUT = 3000L
        private const val TAG = "CommentsRepository"
    }
}