package com.swapnil.weatherapp.features.dashboard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swapnil.weatherapp.features.dashboard.data.utils.Converters
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData

@Database(
    entities = [WeatherData::class], version = 1
)
@TypeConverters(
    value = [Converters::class]
)
abstract class WeatherDb: RoomDatabase(){

    abstract val weatherDao: WeatherDao
    companion object {
        val DATABASE_NAME = "weather_db"
    }

}