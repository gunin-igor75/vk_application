package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import javax.inject.Inject

class LoadRecommendationsUseCase @Inject constructor(
    private val repository: VkRepository
) {
    operator fun invoke() = repository.loadRecommendations()
}