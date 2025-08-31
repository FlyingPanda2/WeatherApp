package com.pandoscorp.weatherapp.data.remote.dto

import com.pandoscorp.weatherapp.domain.model.Weather

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<WeatherDto>,
    val wind: Wind,
    val sys: Sys
) {
    fun toWeather(): Weather {
        return Weather(
            city = name,
            country = sys.country,
            temperature = main.temp,
            feelsLike = main.feels_like,
            description = weather.first().description,
            icon = weather.first().icon,
            humidity = main.humidity,
            windSpeed = wind.speed,
            pressure = main.pressure,
            weatherId = weather.first().id
        )
    }
}

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)