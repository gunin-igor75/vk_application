package com.github.gunin_igor75.vk_application.domain.repository

import com.github.gunin_igor75.vk_application.domain.entity.Comment
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.entity.LoginState
import kotlinx.coroutines.flow.StateFlow

interface VkRepository {

    fun loadRecommendations(): StateFlow<List<FeedPost>>

    fun changeStateAuth(): StateFlow<LoginState>

    fun loadComments(feedPost: FeedPost) : StateFlow<List<Comment>>

    suspend fun loadRecommendationsNext()

    suspend fun changeLikes(feedPost: FeedPost)

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun loadNextComments()

    suspend fun getAuthorization()
}