package com.github.gunin_igor75.vk_application.data.dto.news_feed

import com.github.gunin_igor75.vk_application.data.dto.news_feed.LikesCountDto
import com.google.gson.annotations.SerializedName

data class ResponseLikesCountDto(
    @SerializedName("response") val likesCount: LikesCountDto
)
