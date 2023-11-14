package com.github.gunin_igor75.vk_application.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class LikesCountDto(
    @SerializedName("likes") val count: Int
)
