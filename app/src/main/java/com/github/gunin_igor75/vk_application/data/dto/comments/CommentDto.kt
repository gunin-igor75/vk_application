package com.github.gunin_igor75.vk_application.data.dto.comments

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String,
)
