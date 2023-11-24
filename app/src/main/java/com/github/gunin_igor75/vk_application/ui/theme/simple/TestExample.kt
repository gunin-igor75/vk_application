package com.github.gunin_igor75.vk_application.ui.theme.simple

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.vk_application.ui.theme.DarkBlue

@Preview(showBackground = true)
@Composable
fun TestExample() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "TEST TEST")
    }
}