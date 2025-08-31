package com.pandoscorp.weatherapp.domain.repository

import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): AppResult<Location>
}