package com.pandoscorp.weatherapp.domain.usecase

import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Location
import com.pandoscorp.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): AppResult<Location> {
        return repository.getCurrentLocation()
    }
}