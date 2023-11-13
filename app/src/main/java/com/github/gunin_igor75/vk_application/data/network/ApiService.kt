package com.github.gunin_igor75.vk_application.data.network

import com.github.gunin_igor75.vk_application.data.dto.ResponseNewsFeedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("v") version: String = VERSION
    ): ResponseNewsFeedDto

    companion object{
        private const val VERSION = "5.154"
    }
}