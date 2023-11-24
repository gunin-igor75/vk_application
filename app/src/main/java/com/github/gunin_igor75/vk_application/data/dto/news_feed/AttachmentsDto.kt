package com.github.gunin_igor75.vk_application.data.dto.news_feed

import com.google.gson.annotations.SerializedName

data class AttachmentsDto(
    @SerializedName("photo") val photo: PhotoDto?
)
