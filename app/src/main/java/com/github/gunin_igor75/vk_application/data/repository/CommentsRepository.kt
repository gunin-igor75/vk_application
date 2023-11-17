package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import com.github.gunin_igor75.vk_application.data.mapper.CommentsMapper
import com.github.gunin_igor75.vk_application.domain.Comment
import com.github.gunin_igor75.vk_application.domain.FeedPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentsRepository(
    application: Application
) : VkRepository(application) {


    private val mapper = CommentsMapper()

    fun loadComments(feedPost: FeedPost): Flow<List<Comment>> {
        return flow {
            val response = apiService.getComments(
                token = getTokenAccess(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
            emit(mapper.createComments(response))
        }
    }

    private fun getTokenAccess(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }
}