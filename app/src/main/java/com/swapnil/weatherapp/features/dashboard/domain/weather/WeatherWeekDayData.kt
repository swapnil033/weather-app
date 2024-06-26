package com.swapnil.weatherapp.features.dashboard.domain.weather

import java.time.LocalDateTime

data class WeatherWeekDayData(
    val dateTime: LocalDateTime,
    val day: String,
    val weatherData: List<WeatherData>,
    val isSelected: Boolean = false
)
