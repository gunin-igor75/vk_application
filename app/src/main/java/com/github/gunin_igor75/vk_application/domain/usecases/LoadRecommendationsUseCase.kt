package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.repository.VkRepository

class LoadRecommendationsUseCase(
    private val repository: VkRepository
) {
    operator fun invoke() = repository.loadRecommendations()
}