package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.repository.VkRepository

class ChangeLikesUseCase(
    private val repository: VkRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) = repository.changeLikes(feedPost)
}