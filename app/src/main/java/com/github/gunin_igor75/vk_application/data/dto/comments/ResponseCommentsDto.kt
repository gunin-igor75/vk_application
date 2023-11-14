package com.github.gunin_igor75.vk_application.data.dto.comments

import com.google.gson.annotations.SerializedName

data class ResponseCommentsDto(
    @SerializedName("response") val content: ContainersDto
)
