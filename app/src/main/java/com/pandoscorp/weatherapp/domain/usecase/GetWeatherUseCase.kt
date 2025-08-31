package com.pandoscorp.weatherapp.domain.usecase

import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Weather
import com.pandoscorp.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): AppResult<Weather> {
        return repository.getWeather(city)
    }
}