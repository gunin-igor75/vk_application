package com.github.gunin_igor75.vk_application.domain

import com.github.gunin_igor75.vk_application.R

data class FeedPost(
    val id: Int = -1,
    val community: String = "/dev/null",
    val createAt: String = "16:00",
    val imagePostId: Int = R.drawable.post_content_image,
    val avatarId: Int = R.drawable.post_comunity_thumbnail,
    val content: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(R.drawable.ic_views_count, 916, StatisticType.VIEW),
        StatisticItem(R.drawable.ic_comment, 8, StatisticType.COMMENTS),
        StatisticItem(R.drawable.ic_share, 7, StatisticType.SHARED),
        StatisticItem(R.drawable.ic_like, 23, StatisticType.LIKES)
    )
)