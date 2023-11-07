package com.github.gunin_igor75.vk_application.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.NavBottomComponent
import com.github.gunin_igor75.vk_application.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val feedPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }


    private var _posts = MutableLiveData<List<FeedPost>>(feedPosts)
    val posts = _posts

    private var _selectedNavItem = MutableLiveData<NavBottomComponent>(NavBottomComponent.Home)
    val selectedNavItem = _selectedNavItem

    fun changeCountStatistic(item: StatisticItem, post: FeedPost) {
        val posts = _posts.value?.toMutableList() ?: mutableListOf()
        posts.apply {
            replaceAll {
                if (it.id == post.id) {
                    val oldStatics = it.statistics.toMutableList().apply {
                        replaceAll { newItem ->
                            if (newItem == item) {
                                newItem.copy(count = newItem.count + 1)
                            } else {
                                newItem
                            }
                        }
                    }
                    it.copy(statistics = oldStatics)
                } else {
                    it
                }
            }
        }
        _posts.value = posts
    }

    fun deletePost(post: FeedPost) {
        val postsNew = _posts.value?.toMutableList() ?: mutableListOf()
        postsNew.remove(post)
        _posts.value = postsNew
    }

    fun changeSelectedNavComponent(navBottomComponent: NavBottomComponent) {
        _selectedNavItem.value = navBottomComponent
    }
}