package com.github.gunin_igor75.vk_application.navigation

import android.net.Uri
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.google.gson.Gson

sealed class Screen(
    val route:String
){
    object Home: Screen(ROUTE_HOME)
    object NewsFeed: Screen(ROUTE_NEWS_FEED)
    object Favorite: Screen(ROUTE_FAVORITE)
    object Profile: Screen(ROUTE_PROFILE)
    object Comments: Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostJson = Gson().toJson(feedPost)
            return  "$ROUTE_FOR_ARGS/${feedPostJson.encode()}"
        }
    }


    companion object{
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS_FEED = "news_feed"
        private const val ROUTE_FAVORITE = "favorite"
        private const val ROUTE_PROFILE = "profile"

        const val KEY_FEED_POST = "feed_post"
        private const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
//        const val KEY_FEED_POST_ID = "feed_post_id"
//        const val KEY_CONTENT_TEXT = "content_text"
//        private const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}/{$KEY_CONTENT_TEXT}"
    }

    fun String.encode():String {
        return Uri.encode(this)
    }
}
