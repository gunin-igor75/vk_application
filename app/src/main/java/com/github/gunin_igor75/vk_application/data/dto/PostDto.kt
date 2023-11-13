package com.github.gunin_igor75.vk_application.data.dto

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: String,
    @SerializedName("source_id") val communityId: Long,
    @SerializedName("is_favorite") val isFavorite:Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likesDto: LikesDto,
    @SerializedName("comments") val commentsDto: CommentsDto,
    @SerializedName("views") val viewsDto: ViewsDto,
    @SerializedName("reposts") val repostsDto: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentsDto>?
)
