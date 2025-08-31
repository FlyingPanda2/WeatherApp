package com.pandoscorp.weatherapp.data.repository

import com.pandoscorp.weatherapp.data.remote.api.WeatherApiService
import com.pandoscorp.weatherapp.domain.model.Weather
import com.pandoscorp.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject
import com.pandoscorp.weatherapp.core.common.AppResult

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(city: String): AppResult<Weather> {
        return try {
            val response = apiService.getWeather(city)
            AppResult.Success(response.toWeather())
        } catch (e: Exception) {
            AppResult.Error("Failed to get weather: ${e.message}")
        }
    }

    override suspend fun getWeatherByLocation(lat: Double, lon: Double): AppResult<Weather> {
        return try {
            val response = apiService.getWeatherByLocation(lat, lon)
            AppResult.Success(response.toWeather())
        } catch (e: Exception) {
            AppResult.Error("Failed to get weather by location: ${e.message}")
        }
    }
}