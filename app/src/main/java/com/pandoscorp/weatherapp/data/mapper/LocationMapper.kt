package com.pandoscorp.weatherapp.data.mapper

import com.pandoscorp.weatherapp.domain.model.Location as DomainLocation
import android.location.Location as AndroidLocation

fun AndroidLocation.toDomainLocation(): DomainLocation {
    return DomainLocation(
        lat = this.latitude,
        lon = this.longitude,
        accuracy = this.accuracy
    )
}