package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: VkRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) = repository.deletePost(feedPost)
}