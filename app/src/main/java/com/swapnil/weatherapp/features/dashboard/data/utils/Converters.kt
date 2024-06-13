package com.swapnil.weatherapp.features.dashboard.data.utils

import androidx.room.TypeConverter
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochSecond(it),
                TimeZone.getDefault().toZoneId(),
            )
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)

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