package com.pandoscorp.weatherapp.domain.model

data class Location(
    val lat: Double,
    val lon: Double,
    val accuracy: Float = 0f
)