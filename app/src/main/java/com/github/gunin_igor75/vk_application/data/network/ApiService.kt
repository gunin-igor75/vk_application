package com.github.gunin_igor75.vk_application.data.network

import com.github.gunin_igor75.vk_application.data.dto.comments.ResponseCommentsDto
import com.github.gunin_igor75.vk_application.data.dto.news_feed.ResponseLikesCountDto
import com.github.gunin_igor75.vk_application.data.dto.news_feed.ResponseNewsFeedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("v") version: String = VERSION
    ): ResponseNewsFeedDto

    @GET("newsfeed.getRecommended")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String,
        @Query("v") version: String = VERSION
    ): ResponseNewsFeedDto

    @GET("likes.add")
    suspend fun addLikes(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId:Long,
        @Query("item_id") postId: Long,
        @Query("type") type:String = POST,
        @Query("v") version: String = VERSION
    ): ResponseLikesCountDto

    @GET("likes.delete")
    suspend fun deleteLikes(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId:Long,
        @Query("item_id") postId: Long,
        @Query("type") type:String = POST,
        @Query("v") version: String = VERSION
    ): ResponseLikesCountDto

    @GET("newsfeed.ignoreItem")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId:Long,
        @Query("item_id") postId: Long,
        @Query("type") type:String = WALL,
        @Query("v") version: String = VERSION
    )

    @GET("wall.getComments")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId:Long,
        @Query("post_id") postId: Long,
        @Query("fields") photo: String = FIELDS,
        @Query("extended") extended:Int = EXTENDED,
        @Query("v") version: String = VERSION
    ): ResponseCommentsDto

    companion object{
        private const val VERSION = "5.154"
        private const val POST = "post"
        private const val WALL = "wall"
        private const val EXTENDED = 1
        private const val FIELDS = "photo_100"
    }
}