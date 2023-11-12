package com.github.gunin_igor75.vk_application.domain

import com.github.gunin_igor75.vk_application.R

data class Comment (
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.avatar,
    val textComment: String = "Ling comment text",
    val createAt: String = " 17:00"
)
