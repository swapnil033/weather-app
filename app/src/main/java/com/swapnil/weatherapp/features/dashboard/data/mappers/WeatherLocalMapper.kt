package com.swapnil.weatherapp.features.dashboard.data.mappers

import com.swapnil.weatherapp.features.dashboard.data.remote.WeatherDto
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherInfo
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherType
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherWeekDayData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun WeatherDto.toWeatherLocalData(): List<WeatherData>{

    val list = mutableListOf<WeatherData>()
    weatherData.apply {
        time.forEachIndexed { index, time ->
            val temperature = temperatures[index]
            val weatherCode = weatherCodes[index]
            val windSpeed = windSpeed[index]
            val pressure = pressures[index]
            val humidity = humidities[index]

            list += WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        }
    }

    return  list
}

fun List<WeatherData>.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    val list = mutableListOf<IndexedWeatherData>()

    forEachIndexed { index, weatherData ->
        val time = weatherData.time
        val temperature = weatherData.temperatureCelsius
        val weatherCode = weatherData.weatherType.code
        val windSpeed = weatherData.temperatureCelsius
        val pressure = weatherData.temperatureCelsius
        val humidity = weatherData.temperatureCelsius

        list += IndexedWeatherData(
            index = index, data = WeatherData(
                time = time,
                temperatureCelsius = temperature,
                windSpeed = windSpeed,
                pressure = pressure,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }

    return list.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun List<WeatherData>.toWeatherWeekDataList(): List<WeatherWeekDayData> {

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

fun List<WeatherData>.toWeatherInfo(): WeatherInfo {
    val weatherDataWeek = toWeatherWeekDataList()
    val weatherDataPerDay = toWeatherDataMap()
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