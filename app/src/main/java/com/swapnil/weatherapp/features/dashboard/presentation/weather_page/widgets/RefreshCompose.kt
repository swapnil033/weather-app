package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.swapnil.weatherapp.features.dashboard.presentation.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshCompose(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
){

    val pullToRefreshState = rememberPullToRefreshState()
    Box (
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ){

        content()
        if(pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if(isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )

    }
}

@Preview
@Composable
private fun RefreshComposePreview(){
    WeatherAppTheme {
        RefreshCompose(
            content = {

            },
            isRefreshing = false,
            onRefresh = {

            }
        )
    }
}