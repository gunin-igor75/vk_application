package com.github.gunin_igor75.vk_application.domain

data class FeedPost(
    val id: String,
    val community: String,
    val createAt: String,
    val imagePostId: String?,
    val avatarId: String,
    val content: String,
    val statistics: List<StatisticItem>
)