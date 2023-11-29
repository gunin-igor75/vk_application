package com.github.gunin_igor75.vk_application.presentation.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gunin_igor75.vk_application.R
import com.github.gunin_igor75.vk_application.domain.entity.FeedPost
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsScreenState.CommentState
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsScreenState.InitialState
import com.github.gunin_igor75.vk_application.presentation.comments.CommentsScreenState.Loading
import com.github.gunin_igor75.vk_application.presentation.getComponentNewsApp
import com.github.gunin_igor75.vk_application.ui.theme.DarkBlue


@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    onBackPressed: () -> Unit
) {
    val component = getComponentNewsApp()
        .getCommentScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.commentsFlow.collectAsState(initial = InitialState)
    CommentScreenContent(
        state = state,
        onBackPressed = onBackPressed,
        viewModel = viewModel
    )
}

@Composable
fun CommentScreenContent(
    state: State<CommentsScreenState>,
    onBackPressed: () -> Unit,
    viewModel: CommentsViewModel
) {
    when (val currentState = state.value) {
        is CommentState -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.comments_title)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                onBackPressed()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 72.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = currentState.comments,
                        key = { it.id }
                    ) {
                        CommentItem(comment = it)
                    }
                    item {
                        SideEffect {
                            viewModel.nextLoadingComments()
                        }
                    }
                }
            }
        }

        is InitialState -> {

        }

        is Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }

}