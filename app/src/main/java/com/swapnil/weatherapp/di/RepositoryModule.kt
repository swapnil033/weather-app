package com.swapnil.weatherapp.di

import com.swapnil.weatherapp.data.location.AndroidLocationRepositoryImpl
import com.swapnil.weatherapp.data.repository.WeatherRepositoryImpl
import com.swapnil.weatherapp.domain.location.LocationRepository
import com.swapnil.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        androidLocationRepositoryImpl: AndroidLocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

}