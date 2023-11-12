package com.github.gunin_igor75.vk_application.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.ui.theme.home.NewsFeedScreenState

class NewsFeedViewModel : ViewModel() {

    private val feedPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }



    private val initialState = NewsFeedScreenState.PostState(posts = feedPosts)

    private var _screenState = MutableLiveData<NewsFeedScreenState>(initialState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState

//    private var saveState: HomeScreenState? = HomeScreenState.InitialState

//    fun showComments(feedPost: FeedPost) {
//        saveState = _screenState.value
//        _screenState.value = HomeScreenState.CommentState(
//            feedPost = feedPost,
//            comments = comments
//        )
//    }

//    fun closedComments() {
//        _screenState.value = saveState
//    }

    fun changeCountStatistic(item: StatisticItem, post: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.PostState)  return

        val posts = currentState.posts.toMutableList()
        posts.apply {
            replaceAll {feedPost ->
                if (feedPost.id == post.id) {
                    val oldStatics = feedPost.statistics.toMutableList().apply {
                        replaceAll { newItem ->
                            if (newItem == item) {
                                newItem.copy(count = newItem.count + 1)
                            } else {
                                newItem
                            }
                        }
                    }
                    feedPost.copy(statistics = oldStatics)
                } else {
                    feedPost
                }
            }
        }
        _screenState.value = currentState.copy(posts =posts)
    }

    fun deletePost(post: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.PostState)  return

        val postsNew = currentState.posts.toMutableList()
        postsNew.remove(post)
        _screenState.value = currentState.copy(posts =  postsNew)
    }
}