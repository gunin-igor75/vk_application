package com.github.gunin_igor75.vk_application.domain.entity

data class Comment (
    val id: Long,
    val authorName: String,
    val url: String,
    val text: String,
    val createAt: String
)
