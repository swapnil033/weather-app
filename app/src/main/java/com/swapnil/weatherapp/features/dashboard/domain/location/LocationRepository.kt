package com.swapnil.weatherapp.features.dashboard.domain.location

import android.location.Location

interface LocationRepository {
    suspend fun getLocation(): Location?
}