package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.weatherapp.features.dashboard.data.mappers.toWeatherInfo
import com.swapnil.weatherapp.features.dashboard.domain.location.LocationRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherLocalRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRemoteRepository
import com.swapnil.weatherapp.features.dashboard.domain.util.Resource
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherWeekDayData
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.events.WeatherEvent
import com.swapnil.weatherapp.features.dashboard.presentation.weather_page.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRemoteRepository,
    private val location: LocationRepository,
    private val localRepository: WeatherLocalRepository,
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfoFromLocal(){
        state = state.copy(
            isLoading = true,
            isRefreshing = false,
            errorMessage = null
        )
        localRepository.getWeatherData().onEach { data ->
            if(data.isEmpty()) {
                loadWeatherInfo()
                return@onEach
            }

            state = state.copy(
                weatherInfo = data.toWeatherInfo(),
                isLoading = false,
                isRefreshing = false,
                errorMessage = null,
            )
        }.launchIn(viewModelScope)
    }

    fun loadWeatherInfo(isRefreshing: Boolean = false) {
        val deleteJob = viewModelScope.launch {
            localRepository.deleteWeatherData()
        }
        fun insertJob(data: List<WeatherData> ) = viewModelScope.launch {
            localRepository.addWeather(data)
        }

        viewModelScope.launch {
            state = state.copy(
                isLoading = true, errorMessage = null,
                isRefreshing = isRefreshing
            )

            deleteJob.join()

            location.getLocation()?.let { location ->
                val result = repository.getWeatherData(location.latitude, location.longitude)
                when (result) {
                    is Resource.Success -> {
                        if(result.data == null){
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                isRefreshing = false,
                                errorMessage = "No data found",
                            )
                            return@launch }

                        insertJob(result.data).join()
                        //delay(2000L)
                        loadWeatherInfoFromLocal()




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
