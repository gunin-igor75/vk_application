package com.github.gunin_igor75.vk_application.data.dto.comments

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstname: String,
    @SerializedName("last_name") val lastname: String,
    @SerializedName("photo_100") val url: String,
)