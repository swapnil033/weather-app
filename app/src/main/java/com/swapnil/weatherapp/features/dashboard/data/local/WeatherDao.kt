package com.swapnil.weatherapp.features.dashboard.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swapnil.weatherapp.features.dashboard.domain.weather.WeatherData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weatherData: List<WeatherData>)

    @Query("SELECT * FROM weather_data")
    fun getWeatherData(): Flow<List<WeatherData>>

    @Query("DELETE FROM weather_data")
    suspend fun deleteWeatherData()

    @Query("DELETE FROM weather_data WHERE time =:time")
    suspend fun deleteWeatherDataEntry(time: LocalDateTime)
}