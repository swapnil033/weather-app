package com.swapnil.weatherapp.presentation.weather_page

import com.swapnil.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
