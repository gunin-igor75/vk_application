package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.vk_application.domain.FeedPost
import com.github.gunin_igor75.vk_application.domain.StatisticItem
import com.github.gunin_igor75.vk_application.domain.StatisticType
import com.github.gunin_igor75.vk_application.domain.StatisticType.COMMENTS
import com.github.gunin_igor75.vk_application.domain.StatisticType.LIKES
import com.github.gunin_igor75.vk_application.domain.StatisticType.SHARED
import com.github.gunin_igor75.vk_application.domain.StatisticType.VIEW


@Composable
fun CardPost(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewAndPostClickListener: (StatisticItem) -> Unit,
    onSharedAndPostClickListener: (StatisticItem) -> Unit,
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
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = painterResource(
                    id = feedPost.imagePostId
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistic(
                statistics = feedPost.statistics,
                onViewClickListener = onViewAndPostClickListener,
                onSharedClickListener = onSharedAndPostClickListener,
                onCommentsClickListener = onCommentsAndPostClickListener,
                onLakesClickListener = onLakesAndPostClickListener
            )
        }
    }
}

@Composable
fun Statistic(
    statistics: List<StatisticItem>,
    onViewClickListener: (StatisticItem) -> Unit,
    onSharedClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLakesClickListener: (StatisticItem) -> Unit
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val view = statistics.getByType(VIEW)
            StatisticElement(
                iconId = view.iconId,
                value = view.count.toString(),
                onItemClickListener = {
                    onViewClickListener(view)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val shared = statistics.getByType(SHARED)
            StatisticElement(
                iconId = shared.iconId,
                value = shared.count.toString(),
                onItemClickListener = {
                    onSharedClickListener(shared)
                }
            )
            val comments = statistics.getByType(COMMENTS)
            StatisticElement(
                iconId = comments.iconId,
                value = comments.count.toString(),
                onItemClickListener = {
                    onCommentsClickListener(comments)
                }
            )
            val likes = statistics.getByType(LIKES)
            StatisticElement(
                iconId = likes.iconId,
                value = likes.count.toString(),
                onItemClickListener = {
                    onLakesClickListener(likes)
                }
            )
        }
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
    onItemClickListener: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onItemClickListener()
        }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
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
        Image(
            painter = painterResource(
                id = feedPost.avatarId
            ),
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
