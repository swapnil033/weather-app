package com.swapnil.weatherapp.di

import com.swapnil.weatherapp.features.dashboard.data.location.AndroidLocationRepositoryImpl
import com.swapnil.weatherapp.features.dashboard.data.repository.WeatherLocalRepositoryImpl
import com.swapnil.weatherapp.features.dashboard.data.repository.WeatherRemoteRepositoryImpl
import com.swapnil.weatherapp.features.dashboard.domain.location.LocationRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherLocalRepository
import com.swapnil.weatherapp.features.dashboard.domain.repository.WeatherRemoteRepository
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
        weatherRepositoryImpl: WeatherRemoteRepositoryImpl
    ): WeatherRemoteRepository

    @Binds
    @Singleton
    abstract fun bindWeatherLocalRepository(
        weatherLocalRepositoryImpl: WeatherLocalRepositoryImpl
    ): WeatherLocalRepository

}