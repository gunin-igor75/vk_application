package com.github.gunin_igor75.vk_application.data.mapper

import com.github.gunin_igor75.vk_application.data.dto.comments.ResponseCommentsDto
import com.github.gunin_igor75.vk_application.data.dto.news_feed.ResponseNewsFeedDto
import com.github.gunin_igor75.vk_application.domain.entity.Comment
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.entity.StatisticItem
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.absoluteValue


class VkMapper {
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
                    ),
                    countComment = post.commentsDto.count
                )
            )
        }
        return result
    }

    fun createComments(response: ResponseCommentsDto): List<Comment> {
        val comments = mutableListOf<Comment>()
        val commentsDto = response.content.commentsDto
        val profilesDto = response.content.profilesDto
        for (commentDto in commentsDto) {
            if (commentDto.text.isBlank()) continue
            val profileDto = profilesDto.firstOrNull{commentDto.authorId == it.id }?: continue
            comments.add(
                Comment(
                id = commentDto.id,
                authorName = "${profileDto.firstname} ${profileDto.lastname}",
                text = commentDto.text,
                createAt = convertTime(commentDto.date),
                url = profileDto.url
            )
            )
        }
        return comments
    }

    fun convertTime(date: Long): String {
        val currentDate = Date(date * 1000)
        val pattern = "d MMMM yyyy, HH:mm"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(currentDate)
    }

}