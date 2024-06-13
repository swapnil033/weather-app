package com.swapnil.weatherapp.features.dashboard.domain.repository

import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherLocalRepository {
    suspend fun addWeather(weatherData: List<WeatherData>)
    fun getWeatherData(): Flow<List<WeatherData>>
    suspend fun deleteWeatherData()
}