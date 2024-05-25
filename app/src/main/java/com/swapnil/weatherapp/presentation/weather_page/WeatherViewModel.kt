package com.swapnil.weatherapp.presentation.weather_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.weatherapp.domain.location.LocationRepository
import com.swapnil.weatherapp.domain.repository.WeatherRepository
import com.swapnil.weatherapp.domain.util.Resource
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
}