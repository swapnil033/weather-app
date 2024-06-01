package com.swapnil.weatherapp.presentation.weather_page.events

import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherWeekDayData

sealed class WeatherEvent {
    data class OnDayChange(val weatherWeekDayData: WeatherWeekDayData) : WeatherEvent()
    data class OnHourChange(val weatherData: WeatherData) : WeatherEvent()
}