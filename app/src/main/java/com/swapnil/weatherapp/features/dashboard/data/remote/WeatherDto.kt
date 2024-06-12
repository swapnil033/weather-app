package com.swapnil.weatherapp.features.dashboard.data.remote

import com.squareup.moshi.Json

data class WeatherDto (
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)