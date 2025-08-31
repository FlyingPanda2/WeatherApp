package com.pandoscorp.weatherapp.presentation.weather.state

import com.pandoscorp.weatherapp.domain.model.Weather

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val weather: Weather) : WeatherState()
    data class Error(val message: String) : WeatherState()
}