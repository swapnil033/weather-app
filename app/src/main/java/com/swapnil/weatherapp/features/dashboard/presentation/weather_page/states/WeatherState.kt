package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.states

import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherInfo
import java.time.LocalDateTime

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val currentHour: Int = LocalDateTime.now().hour,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
