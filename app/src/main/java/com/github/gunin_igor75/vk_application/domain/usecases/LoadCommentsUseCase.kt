package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import javax.inject.Inject

class LoadCommentsUseCase @Inject constructor(
    private val repository: VkRepository
) {
    operator fun invoke(feedPost: FeedPost) = repository.loadComments(feedPost)
}