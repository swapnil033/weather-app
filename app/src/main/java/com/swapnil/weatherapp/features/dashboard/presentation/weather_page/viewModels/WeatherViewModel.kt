package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.weatherapp.features.dashboard.domain.location.LocationRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRepository
import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherWeekDayData
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.events.WeatherEvent
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val location: LocationRepository,
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true, errorMessage = null
            )
            location.getLocation()?.let { location ->
                val result = repository.getWeatherData(location.latitude, location.longitude)

                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            errorMessage = null,
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            errorMessage = result.message,
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    errorMessage = "Couldn't retrieve location. Make sure to grant the permission and enable GPS."
                )
            }
        }
    }

    fun onAction(action: WeatherEvent) {
        when (action) {
            is WeatherEvent.OnHourChange -> {changeHour(action.weatherData)}
            is WeatherEvent.OnDayChange -> changeDay(action.weatherWeekDayData)
        }
    }

    private fun changeHour(weatherData: WeatherData) {
        state = state.copy(
            weatherInfo = state.weatherInfo?.copy(
                currentWeatherData = weatherData
            )
        )
    }

    private fun changeDay(day: WeatherWeekDayData) {
        state = state.copy(
            weatherInfo = state.weatherInfo?.weatherDataWeek?.map {
                it.copy(isSelected = it.dateTime.dayOfWeek == day.dateTime.dayOfWeek)
            }?.let {
                state.weatherInfo?.copy(
                    currentDay = day.weatherData,
                    weatherDataWeek = it.toList()
                )
            }
        )
    }
}
