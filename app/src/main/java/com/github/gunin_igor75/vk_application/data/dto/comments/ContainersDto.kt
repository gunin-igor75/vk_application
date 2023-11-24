package com.github.gunin_igor75.vk_application.data.dto.comments

import com.google.gson.annotations.SerializedName

data class ContainersDto(
    @SerializedName("items") val commentsDto: List<CommentDto>,
    @SerializedName("profiles") val profilesDto: List<ProfileDto>
)
