package com.swapnil.weatherapp.features.dashboard.domain.use_cases

import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherLocalRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRemoteRepository
import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import java.time.LocalDateTime
import javax.inject.Inject

class GetRemoteDataUseCase @Inject constructor(
    private val localRepository: WeatherLocalRepository,
    private val repository: WeatherRemoteRepository,
) {

    suspend operator fun invoke(lat: Double, long: Double) : Resource<List<WeatherData>>{

        val today = LocalDateTime.now()
            .withHour(0)
            .withMinute(0)
            .withSecond(0)

        val oldDays = localRepository.getWeatherData().first()
            .filter { it.time.isBefore(today) }

        if(oldDays.size > 1) {
            for (oldHour in oldDays) {
                localRepository.deleteOldData(oldHour.time)
            }
        }

        return when(val apiCall = repository.getWeatherData(lat, long)){
            is Resource.Error -> {
                Resource.Error(apiCall.message ?: "Api error")
            }

            is Resource.Success -> {
                apiCall.data?.let { localRepository.addWeather(it) }
                Resource.Success(apiCall.data)
            }
        }

    }


}