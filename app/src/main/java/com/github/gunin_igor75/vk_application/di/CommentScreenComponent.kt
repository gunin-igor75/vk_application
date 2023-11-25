package com.github.gunin_igor75.vk_application.di

import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentViewModelModule::class
    ]
)
interface CommentScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentScreenComponent
    }
}