package com.github.gunin_igor75.vk_application.data.repository

import android.app.Application
import com.github.gunin_igor75.vk_application.data.mapper.CommentsMapper
import com.github.gunin_igor75.vk_application.data.network.ApiFactory
import com.github.gunin_igor75.vk_application.domain.Comment
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class CommentsRepository(
    application: Application
) {

    private val storage = VKPreferencesKeyValueStorage(application)

    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService

    private val mapper = CommentsMapper()

    suspend fun loadComments(feedPost: FeedPost): List<Comment> {
        val response = apiService.getComments(
            token = getTokenAccess(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.createComments(response)
    }

    private fun getTokenAccess(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }
}