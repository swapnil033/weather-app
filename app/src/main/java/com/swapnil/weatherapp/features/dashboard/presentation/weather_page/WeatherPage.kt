package com.swapnil.weatherapp.features.dashboard.presentation.weather_page

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.swapnil.weatherapp.core.presentation.permission.PermissionViewModel
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherWeekDayData
import com.swapnil.weatherapp.features.dashboard.presentation.ui.theme.DarkBlue
import com.swapnil.weatherapp.features.dashboard.presentation.ui.theme.DeepBlue
import com.swapnil.weatherapp.features.dashboard.presentation.ui.theme.WeatherAppTheme
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.events.WeatherEvent
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.states.WeatherState
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.viewModels.WeatherViewModel
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets.RefreshCompose
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets.WeatherCard
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets.WeatherForecast
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets.WeekDisplay
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private val permissionsToRequest = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
)

@Composable
fun WeatherScreenRoot(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel(),
    permissionViewModel: PermissionViewModel = hiltViewModel(),
) {

    val dialogQueue = permissionViewModel.visiblePermissionDialogQueue

    val multiplePermissionResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                permissionsToRequest.forEach { permission ->
                    permissionViewModel.onPermissionResult(
                        permission = permission, isGranted = perms[permission] == true
                    )
                }

                if (perms.values.contains(false)) return@rememberLauncherForActivityResult

                viewModel.loadWeatherInfoFromLocal()
            })

    LaunchedEffect(true) {
        multiplePermissionResultLauncher.launch(
            permissionsToRequest
        )
    }

    WeatherScreen(state = viewModel.state,
        onDaySelect = { viewModel.onAction(WeatherEvent.OnDayChange(it)) },
        onHourSelect = { viewModel.onAction(WeatherEvent.OnHourChange(it)) },
        onRefresh = { viewModel.loadWeatherInfo() },
        )
}

@Composable
private fun WeatherScreen(
    state: WeatherState,
    onDaySelect: (WeatherWeekDayData) -> Unit,
    onHourSelect: (WeatherData) -> Unit,
    onRefresh: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    RefreshCompose(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            scope.launch {
                onRefresh()
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = DarkBlue)
            ) {
                item {
                    WeatherCard(state = state, backgroundColor = DeepBlue)
                    Spacer(modifier = Modifier.height(16.dp))
                    state.weatherInfo?.weatherDataWeek?.let {
                        WeekDisplay(it, modifier = Modifier.padding(horizontal = 16.dp)) {
                            onDaySelect(it)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    state.weatherInfo?.currentDay?.let { data ->
                        WeatherForecast(
                            title = "Today",
                            selectedHour = state.weatherInfo.currentWeatherData?.time?.hour!!,
                            data = data,
                        ) { onHourSelect(it) }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }



}


@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherAppTheme {
        val state = WeatherState()
        WeatherScreen(
            state = state,
            onDaySelect = {},
            onHourSelect = {},
            onRefresh = {},
        )
    }
}