package com.swapnil.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.swapnil.weatherapp.domain.location.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidLocationRepositoryImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient, private val application: Application,
) : LocationRepository {
    override suspend fun getLocation(): Location? {
        val hasAccessFineLocationPermission =
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val hasAccessCoarseLocationPermission =
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!hasAccessFineLocationPermission) return null
        if (!hasAccessCoarseLocationPermission) return null
        if (!isGpsEnabled) return null

        return suspendCancellableCoroutine { cont ->

            locationClient.lastLocation.apply {
                if (isSuccessful) {
                    if (isComplete) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }

        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            application, permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}