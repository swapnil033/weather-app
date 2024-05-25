package com.swapnil.weatherapp.domain.repository

import com.swapnil.weatherapp.domain.util.Resource
import com.swapnil.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}