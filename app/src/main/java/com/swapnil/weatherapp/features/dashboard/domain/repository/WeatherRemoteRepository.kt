package com.swapnil.weatherapp.features.dashboard.domain.repository

import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData

interface WeatherRemoteRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<List<WeatherData>>
}