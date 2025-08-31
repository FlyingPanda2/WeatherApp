package com.pandoscorp.weatherapp.domain.repository

import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(city: String): AppResult<Weather>
    suspend fun getWeatherByLocation(lat: Double, lon: Double): AppResult<Weather>
}