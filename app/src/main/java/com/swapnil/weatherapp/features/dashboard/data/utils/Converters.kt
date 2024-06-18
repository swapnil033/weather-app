package com.swapnil.weatherapp.features.dashboard.data.utils

import androidx.room.TypeConverter
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            it.format(formatter)
        }

    }
    @TypeConverter
    fun fromWeatherType(value: String?): WeatherType {
        return WeatherType.fromDesc(value)
    }

    @TypeConverter
    fun weatherTypeToString(date: WeatherType?): String? {
        return date?.weatherDesc
    }
}