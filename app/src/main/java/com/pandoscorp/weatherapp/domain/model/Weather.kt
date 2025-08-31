package com.pandoscorp.weatherapp.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val weatherId: Int
)