package com.github.gunin_igor75.vk_application.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class NewsFeedDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDTO>,
    @SerializedName("next_from") val nextFrom: String?
)
