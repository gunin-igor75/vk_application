package com.github.gunin_igor75.vk_application.domain.entity

data class StatisticItem(
    val count: Int,
    val type: StatisticType
)
enum class StatisticType {
    VIEW, SHARED, COMMENTS, LIKES
}