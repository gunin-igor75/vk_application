package com.github.gunin_igor75.vk_application.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("photo_200") val avatarUrl: String
)
