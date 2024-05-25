package com.swapnil.weatherapp.data.mappers

import com.swapnil.weatherapp.data.remote.WeatherDataDto
import com.swapnil.weatherapp.data.remote.WeatherDto
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherInfo
import com.swapnil.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeed[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index,
            data =
            WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                windSpeed = windSpeed,
                pressure = pressure,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo{

    val weatherDataPerDay = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataPerDay[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataPerDay,
        currentWeatherData = currentWeatherData,
    )
}