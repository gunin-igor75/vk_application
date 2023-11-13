package com.github.gunin_igor75.vk_application.data.dto

import com.google.gson.annotations.SerializedName

data class ResponseNewsFeedDto(
    @SerializedName("response") val response: NewsFeedDto
)
