package com.github.gunin_igor75.vk_application.domain

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.github.gunin_igor75.vk_application.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Int = -1,
    val community: String = "/dev/null",
    val createAt: String = "16:00",
    val imagePostId: Int = R.drawable.post_content_image,
    val avatarId: Int = R.drawable.post_comunity_thumbnail,
    val content: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(R.drawable.ic_views_count, 916, StatisticType.VIEW),
        StatisticItem(R.drawable.ic_comment, 8, StatisticType.COMMENTS),
        StatisticItem(R.drawable.ic_share, 7, StatisticType.SHARED),
        StatisticItem(R.drawable.ic_like, 23, StatisticType.LIKES)
    )
): Parcelable {


    companion object {
        val NavigationType = object : NavType<FeedPost>(false){
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, FeedPost::class.java)
                } else {
                     bundle.getParcelable<FeedPost>(key)
                }
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }
}


