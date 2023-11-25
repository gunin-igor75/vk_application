package com.github.gunin_igor75.vk_application.domain.usecases

import com.github.gunin_igor75.vk_application.domain.repository.VkRepository
import javax.inject.Inject

class GetAuthorizationUseCase @Inject constructor(
    private val repository: VkRepository
) {
    suspend operator fun invoke() = repository.getAuthorization()
}