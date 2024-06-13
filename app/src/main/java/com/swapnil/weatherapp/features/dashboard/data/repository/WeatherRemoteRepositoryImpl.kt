package com.swapnil.weatherapp.features.dashboard.data.repository

import com.swapnil.weatherapp.features.dashboard.data.mappers.toWeatherLocalData
import com.swapnil.weatherapp.features.dashboard.data.remote.WeatherApi
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRemoteRepository
import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import javax.inject.Inject

class WeatherRemoteRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRemoteRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<List<WeatherData>> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat, long).toWeatherLocalData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}