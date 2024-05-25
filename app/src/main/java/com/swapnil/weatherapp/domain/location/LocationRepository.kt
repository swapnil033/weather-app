package com.swapnil.weatherapp.domain.location

import android.location.Location

interface LocationRepository {
    suspend fun getLocation(): Location?
}