package com.github.gunin_igor75.vk_application.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val iconId: Int,
    val count: Int,
    val type: StatisticType
): Parcelable
enum class StatisticType {
    VIEW, SHARED, COMMENTS, LIKES
}