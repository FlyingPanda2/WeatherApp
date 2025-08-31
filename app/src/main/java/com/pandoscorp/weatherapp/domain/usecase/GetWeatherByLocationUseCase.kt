package com.pandoscorp.weatherapp.domain.usecase

import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Weather
import com.pandoscorp.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): AppResult<Weather> {
        return repository.getWeatherByLocation(lat, lon)
    }
}