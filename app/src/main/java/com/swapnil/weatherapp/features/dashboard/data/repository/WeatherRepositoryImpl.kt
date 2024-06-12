package com.swapnil.weatherapp.features.dashboard.data.repository

import com.swapnil.weatherapp.features.dashboard.data.mappers.toWeatherInfo
import com.swapnil.weatherapp.features.dashboard.data.remote.WeatherApi
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRepository
import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat, long).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}