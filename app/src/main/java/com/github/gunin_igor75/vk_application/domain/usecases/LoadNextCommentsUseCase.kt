package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.repository.VkRepository

class LoadNextCommentsUseCase(
    private val repository: VkRepository
) {
    suspend operator fun invoke() = repository.loadNextComments()
}