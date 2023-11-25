package com.github.gunin_igor75.vk_application.di

import android.app.Application
import com.github.gunin_igor75.vk_application.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun getCommentScreenComponentFactory(): CommentScreenComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}