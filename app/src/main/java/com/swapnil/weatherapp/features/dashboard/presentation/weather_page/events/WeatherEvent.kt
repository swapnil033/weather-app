package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.events

import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherWeekDayData

sealed class WeatherEvent {
    data class OnDayChange(val weatherWeekDayData: WeatherWeekDayData) : WeatherEvent()
    data class OnHourChange(val weatherData: WeatherData) : WeatherEvent()
}