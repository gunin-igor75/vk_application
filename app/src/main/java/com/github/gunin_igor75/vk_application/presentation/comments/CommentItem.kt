package com.github.gunin_igor75.vk_application.presentation.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.vk_application.domain.Comment


@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${comment.authorName}   ${comment.id}",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.textComment,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.createAt,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun VIewPrev() {
    CommentItem(comment = Comment(id = 0))
}
