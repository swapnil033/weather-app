package com.swapnil.weatherapp.features.dashboard.data.repository

import com.swapnil.weatherapp.features.dashboard.data.local.WeatherDao
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherLocalRepository
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocalRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
): WeatherLocalRepository {
    override suspend fun addWeather(weatherData: List<WeatherData>) {
        weatherDao.addWeather(weatherData)
    }

    override fun getWeatherData(): Flow<List<WeatherData>> {
        return weatherDao.getWeatherData()
    }

    override suspend fun deleteWeatherData() {
        weatherDao.deleteWeatherData()
    }
}