package com.github.gunin_igor75.vk_application.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.vk_application.domain.NavBottomComponent.Favorite
import com.github.gunin_igor75.vk_application.domain.NavBottomComponent.Home
import com.github.gunin_igor75.vk_application.domain.NavBottomComponent.Profile
import com.github.gunin_igor75.vk_application.presentation.MainViewModel


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val selectedPosition by viewModel.selectedNavItem.observeAsState(Home)
                val list = listOf(Home, Favorite, Profile)
                list.forEach { item ->
                    BottomNavigationItem(
                        selected = selectedPosition == item,
                        onClick = {
                            viewModel.changeSelectedNavComponent(item)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.labelId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { padding ->
        val state = viewModel.posts.observeAsState(listOf())
        val listModel = state.value ?: mutableListOf()
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                listModel.size,
                key = { i ->
                    listModel[i].id
                }
            ) { index ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deletePost(listModel[index])
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                        ) {
                        }
                    }
                ) {
                    CardPost(
                        feedPost = listModel[index],
                        onViewAndPostClickListener = { statisticItem ->
                            viewModel.changeCountStatistic(statisticItem, listModel[index])
                        },
                        onSharedAndPostClickListener = { statisticItem ->
                            viewModel.changeCountStatistic(statisticItem, listModel[index])
                        },
                        onCommentsAndPostClickListener = { statisticItem ->
                            viewModel.changeCountStatistic(statisticItem, listModel[index])
                        },
                        onLakesAndPostClickListener = { statisticItem ->
                            viewModel.changeCountStatistic(statisticItem, listModel[index])
                        }
                    )
                }
            }
        }
    }
}

