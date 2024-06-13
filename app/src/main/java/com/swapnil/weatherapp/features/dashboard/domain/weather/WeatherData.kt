package com.swapnil.weatherapp.features.dashboard.domain.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType
)
