package com.swapnil.weatherapp.data.mappers

import com.swapnil.weatherapp.data.remote.WeatherDataDto
import com.swapnil.weatherapp.data.remote.WeatherDto
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherInfo
import com.swapnil.weatherapp.domain.weather.WeatherType
import com.swapnil.weatherapp.domain.weather.WeatherWeekDayData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private data class IndexedWeatherData(
    val index: Int, val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeed[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index, data = WeatherData(
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


fun WeatherDataDto.toWeatherWeekDataList(): List<WeatherWeekDayData> {

    val weatherDataPerDay = this.toWeatherDataMap()

    val list = mutableListOf<WeatherWeekDayData>()
    weatherDataPerDay.forEach {
        val relativeDay = it.key

        val day = LocalDateTime.now().plusDays(relativeDay.toLong())

        val dayName = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

        list += WeatherWeekDayData(
            dateTime = day,
            day = dayName,
            weatherData = it.value,
            isSelected = relativeDay == 0
        )
    }
    return list
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {

    val weatherDataWeek = weatherData.toWeatherWeekDataList()
    val weatherDataPerDay = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()

    val currentDay = weatherDataPerDay[0]!!
    val currentWeatherData = currentDay.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataWeek = weatherDataWeek,
        weatherDataPerDay = weatherDataPerDay,
        currentDay = currentDay,
        currentWeatherData = currentWeatherData,
    )
}