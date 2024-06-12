package com.swapnil.weatherapp.features.dashboard.domain.weather

data class WeatherInfo(
    val weatherDataWeek: List<WeatherWeekDayData>,
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentDay: List<WeatherData>,
    val currentWeatherData: WeatherData?
)
