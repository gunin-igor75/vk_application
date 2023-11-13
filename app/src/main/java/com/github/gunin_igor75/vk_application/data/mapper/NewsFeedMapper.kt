package com.github.gunin_igor75.vk_application.data.mapper

import com.github.gunin_igor75.vk_application.data.dto.ResponseNewsFeedDto
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.absoluteValue


class NewsFeedMapper {
    fun responseNewsFeedDtoToFeedPost(responseNewsFeedDto: ResponseNewsFeedDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseNewsFeedDto.response.posts
        val groups = responseNewsFeedDto.response.groups
        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            result.add(
                FeedPost(
                    id = post.id,
                    community = group.name,
                    createAt = convertTime(post.date),
                    avatarId = group.avatarUrl,
                    content = post.text,
                    imagePostId = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                    statistics = listOf(
                        StatisticItem(
                            post.viewsDto.count,
                            StatisticType.VIEW
                        ),
                        StatisticItem(
                            post.commentsDto.count,
                            StatisticType.COMMENTS
                        ),
                        StatisticItem(
                            post.repostsDto.count,
                            StatisticType.SHARED
                        ),
                        StatisticItem(
                            post.likesDto.count,
                            StatisticType.LIKES
                        )
                    )
                )
            )
        }
        return result
    }

    private fun convertTime(date: Long): String {
        val currentDate = Date(date * 1000)
        val pattern = "d MMMM yyyy, HH:mm"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(currentDate)
    }
}