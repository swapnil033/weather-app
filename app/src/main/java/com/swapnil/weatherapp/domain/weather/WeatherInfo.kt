package com.swapnil.weatherapp.domain.weather

data class WeatherInfo(
    val weatherDataWeek: List<WeatherWeekDayData>,
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentDay: List<WeatherData>,
    val currentWeatherData: WeatherData?
)
