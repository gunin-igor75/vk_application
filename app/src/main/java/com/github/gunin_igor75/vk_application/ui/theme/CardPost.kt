package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.vk_application.R


@Composable
fun CardPost() {
    Card(

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            HeaderPost()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(
                    id = R.drawable.post_content_image
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistic()
        }
    }

}

@Composable
fun Statistic() {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            StatisticElement(iconId = R.drawable.ic_views_count, value = "916")
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatisticElement(iconId = R.drawable.ic_share, value = "7")
            StatisticElement(iconId = R.drawable.ic_comment, value = "8")
            StatisticElement(iconId = R.drawable.ic_like, value = "23")
        }
    }

}

@Composable
fun StatisticElement(iconId: Int, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
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
fun HeaderPost() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.post_comunity_thumbnail
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
                text = "/dev/null",
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "14:00",
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


@Preview
@Composable
fun PreviewLight() {
    Vk_applicationTheme(
        darkTheme = false
    ) {
        CardPost()
    }
}

@Preview
@Composable
fun PreviewNight() {
    Vk_applicationTheme(
        darkTheme = true
    ) {
        CardPost()
    }
}