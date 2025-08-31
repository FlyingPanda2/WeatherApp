// data/repository/LocationRepositoryImpl.kt
package com.pandoscorp.weatherapp.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import android.location.Location as AndroidLocation
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.data.mapper.toDomainLocation
import com.pandoscorp.weatherapp.domain.model.Location
import com.pandoscorp.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    private val context: Context
) : LocationRepository {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentLocation(): AppResult<Location> {
        return try {
            if (!hasLocationPermission()) {
                return AppResult.Error("Location permission not granted")
            }

            val location = getCurrentLocationWithPermissions()

            if (location != null) {
                AppResult.Success(location.toDomainLocation())
            } else {
                AppResult.Error("Location not available")
            }
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Unknown location error")
        }
    }

    @androidx.annotation.RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ]
    )
    private suspend fun getCurrentLocationWithPermissions(): AndroidLocation? {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                createCancellationToken()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result)
                } else {
                    continuation.resume(null)
                }
            }
        }
    }

    private fun createCancellationToken(): CancellationToken {
        return object : CancellationToken() {
            override fun onCanceledRequested(listener: OnTokenCanceledListener): CancellationToken {
                return CancellationTokenSource().token
            }
            override fun isCancellationRequested(): Boolean = false
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
}