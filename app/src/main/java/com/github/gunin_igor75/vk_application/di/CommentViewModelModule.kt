package com.github.gunin_igor75.vk_application.di

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(commentsViewModel: CommentsViewModel): ViewModel

}