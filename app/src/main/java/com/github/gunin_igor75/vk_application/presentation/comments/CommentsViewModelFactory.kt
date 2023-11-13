package com.github.gunin_igor75.vk_application.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.vk_application.domain.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}