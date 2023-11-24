package com.github.gunin_igor75.vk_application.presentation.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.gunin_igor75.vk_application.R
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.domain.entity.StatisticItem
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType.COMMENTS
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType.LIKES
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType.SHARED
import com.github.gunin_igor75.vk_application.domain.entity.StatisticType.VIEW
import com.github.gunin_igor75.vk_application.ui.theme.DarkRed


@Composable
fun CardPost(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onCommentsAndPostClickListener: (StatisticItem) -> Unit,
    onLakesAndPostClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            HeaderPost(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.content)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                model = feedPost.url,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistic(
                feedPost.isUserLikes,
                statistics = feedPost.statistics,
                onCommentsClickListener = onCommentsAndPostClickListener,
                onLakesClickListener = onLakesAndPostClickListener
            )
        }
    }
}

@Composable
fun Statistic(
    isFavorite: Boolean,
    statistics: List<StatisticItem>,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLakesClickListener: (StatisticItem) -> Unit
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val view = statistics.getByType(VIEW)
            StatisticElement(
                iconId = R.drawable.ic_views_count,
                value = convertCount(view.count)
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val shared = statistics.getByType(SHARED)
            StatisticElement(
                iconId = R.drawable.ic_share,
                value = convertCount(shared.count)
            )
            val comments = statistics.getByType(COMMENTS)
            StatisticElement(
                iconId = R.drawable.ic_comment,
                value = convertCount(comments.count),
                onItemClickListener = {
                    onCommentsClickListener(comments)
                }
            )
            val like = statistics.getByType(LIKES)
                StatisticElement(
                iconId = if (isFavorite) R.drawable.ic_like_set else R.drawable.ic_like,
                value = convertCount(like.count),
                onItemClickListener = {
                    onLakesClickListener(like)
                },
                tint = if (isFavorite) DarkRed else MaterialTheme.colors.onSecondary
            )
        }
    }

}

private fun convertCount(number: Int): String {
    return if (number > 1_000_000) {
        String.format("%dK", number / 1000)
    } else if (number > 1000) {
        String.format("%.1fK", number / 1000f)
    } else {
        number.toString()
    }
}

private fun List<StatisticItem>.getByType(type: StatisticType): StatisticItem {
    return this.find {
        it.type == type
    } ?: throw IllegalStateException("Type $type does not exist")
}

@Composable
fun StatisticElement(
    iconId: Int,
    value: String,
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colors.onSecondary
) {
    val modifier = if (onItemClickListener == null) Modifier else Modifier.clickable {
        onItemClickListener()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = MaterialTheme.colors.onSecondary
        )
    }

}

@Composable
fun HeaderPost(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.avatarId,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = feedPost.community,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.createAt,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

