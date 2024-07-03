package com.swapnil.weatherapp.features.dashboard.domain.repository

import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface WeatherLocalRepository {
    suspend fun addWeather(weatherData: List<WeatherData>)
    fun getWeatherData(): Flow<List<WeatherData>>
    suspend fun deleteWeatherData()
    suspend fun deleteOldData(hour: LocalDateTime)
}