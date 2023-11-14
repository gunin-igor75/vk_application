package com.github.gunin_igor75.vk_application.data.mapper

import com.github.gunin_igor75.vk_application.data.dto.comments.ResponseCommentsDto
import com.github.gunin_igor75.vk_application.domain.Comment

class CommentsMapper {

    fun createComments(response: ResponseCommentsDto): List<Comment> {
        val comments = mutableListOf<Comment>()
        val commentsDto = response.content.commentsDto
        val profilesDto = response.content.profilesDto
        for (commentDto in commentsDto) {
            if (commentDto.text.isBlank()) continue
            val profileDto = profilesDto.firstOrNull{commentDto.authorId == it.id }?: continue
            comments.add(Comment(
                id = commentDto.id,
                authorName = "${profileDto.firstname} ${profileDto.lastname}",
                text = commentDto.text,
                createAt = convertTime(commentDto.date),
                url = profileDto.url
            ))
        }
        return comments
    }
}