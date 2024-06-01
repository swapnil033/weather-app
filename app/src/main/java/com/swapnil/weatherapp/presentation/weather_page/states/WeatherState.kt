package com.swapnil.weatherapp.presentation.weather_page.states

import com.swapnil.weatherapp.domain.weather.WeatherInfo
import java.time.LocalDateTime

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val currentHour: Int = LocalDateTime.now().hour,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
