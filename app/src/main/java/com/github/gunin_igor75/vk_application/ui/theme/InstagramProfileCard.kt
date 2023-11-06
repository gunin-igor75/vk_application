package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.vk_application.R


@Composable
fun InstagramCard(viewModel: MainViewModel) {

//    val isFollowed  by viewModel.isFollowed.observeAsState(false)
    val isFollowed = viewModel.isFollowed.observeAsState(false)

    Card(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp),

        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(5.dp)
                )
                TwoElement("Posts", "6,950")
                TwoElement("Followers", "436M")
                TwoElement("Following", "76")
            }
            Text(
                text = "Instagram",
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = "#YoursToMake",
                fontSize = 12.sp
            )
            Text(
                text = "www.facebook.com/emotional_health",
                fontSize = 12.sp
            )
            FollowButton(isFollowed = isFollowed) {
               viewModel.inversionFollowed()
            }
        }
    }
}

@Composable
private fun FollowButton(
    isFollowed: State<Boolean>,
    clickListener: () -> Unit
) {
    OutlinedButton(
        onClick = {
            clickListener()
        },
        shape = RoundedCornerShape(4.dp),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isFollowed.value) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else MaterialTheme.colors.primary
        )
    ) {
        val text = if (!isFollowed.value) {
            "Follow"
        } else {
            "Unfollow"
        }
        Text(text = text)
    }
}

@Composable
private fun TwoElement(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .height(80.dp)
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview
//@Composable
//fun PreviewCardLight() {
//    Vk_applicationTheme(
//        darkTheme = false,
//    ) {
//        InstagramCard()
//    }
//}
//
//@Preview
//@Composable
//fun PreviewCardNight() {
//    Vk_applicationTheme(
//        darkTheme = true,
//    ) {
//        InstagramCard()
//    }
//}