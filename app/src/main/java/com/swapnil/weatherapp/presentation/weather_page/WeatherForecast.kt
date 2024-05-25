package com.swapnil.weatherapp.presentation.weather_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherInfo
import com.swapnil.weatherapp.domain.weather.WeatherType
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
){
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Today", fontSize = 20.sp, color = Color.White,)
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                items(data){weatherData ->
                    HourlyWeatherDisplay(weatherData = weatherData, modifier = Modifier.height(100.dp).padding(16.dp))
                }
            }
        }
    }


}

@Preview
@Composable
private fun WeatherForecastPreview(){
    WeatherAppTheme {
        val weather = WeatherData(
            time = LocalDateTime.now(),
            temperatureCelsius = 0.0,
            pressure = 0.0,
            humidity = 0.0,
            windSpeed = 0.0,
            weatherType = WeatherType.fromWMO(0)
        )
        val weatherMap: List<WeatherData> = listOf(
            weather,weather,weather,weather,weather,weather,weather
        )

        val state = WeatherState(
            weatherInfo = WeatherInfo(
                weatherDataPerDay = mapOf(
                    0 to weatherMap
                ), currentWeatherData = WeatherData(
                    time = LocalDateTime.now(),
                    temperatureCelsius = 0.0,
                    pressure = 0.0,
                    humidity = 0.0,
                    windSpeed = 0.0,
                    weatherType = WeatherType.fromWMO(0)
                )
            )
        )
        WeatherForecast(state = state)
    }
}