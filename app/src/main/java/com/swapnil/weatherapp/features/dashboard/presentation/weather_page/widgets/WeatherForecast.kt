package com.swapnil.weatherapp.features.dashboard.presentation.weather_page.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherType
import com.swapnil.weatherapp.features.dashboard.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime

@Composable
fun WeatherForecast(
    title: String,
    data: List<WeatherData>,
    selectedHour: Int,
    modifier: Modifier = Modifier,
    onHourSelect: (WeatherData) -> Unit
){


    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(max = 1000.dp)
    ) {
        items(data){weatherData ->
            HourlyWeatherDisplay(
                weatherData = weatherData,
                isSelectedHour = weatherData.time.hour == selectedHour,
                modifier = Modifier
                .height(120.dp)
                .padding(horizontal = 0.dp, vertical = 10.dp)
            ){ onHourSelect(it) }
        }
    }

    /*Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = title, fontSize = 20.sp, color = Color.White,)
        Spacer(modifier = Modifier.height(16.dp))
        *//*LazyRow {
            items(data){weatherData ->
                HourlyWeatherDisplay(weatherData = weatherData, modifier = Modifier
                    .height(100.dp)
                    .padding(16.dp))
            }
        }*//*
    }*/


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

        WeatherForecast(
            title = "Today",
            selectedHour = 2,
            data = weatherMap,
        ){}
    }
}