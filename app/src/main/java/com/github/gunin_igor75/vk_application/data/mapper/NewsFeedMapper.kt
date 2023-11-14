package com.github.gunin_igor75.vk_application.data.mapper

import com.github.gunin_igor75.vk_application.data.dto.news_feed.ResponseNewsFeedDto
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.domain.StatisticType
import kotlin.math.absoluteValue


class NewsFeedMapper {
    fun responseNewsFeedDtoToFeedPost(responseNewsFeedDto: ResponseNewsFeedDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseNewsFeedDto.content.posts
        val groups = responseNewsFeedDto.content.groups
        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            result.add(
                FeedPost(
                    id = post.id,
                    communityId = post.communityId,
                    community = group.name,
                    createAt = convertTime(post.date),
                    avatarId = group.avatarUrl,
                    content = post.text,
                    url = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                    isUserLikes = post.likesDto.userLikes > 0,
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
}