package com.github.gunin_igor75.vk_application.domain

data class FeedPost(
    val id: Long,
    val communityId: Long,
    val community: String,
    val createAt: String,
    val url: String?,
    val avatarId: String,
    val content: String,
    val isUserLikes: Boolean,
    val statistics: List<StatisticItem>,
    val countComment: Int
)