package com.pandoscorp.weatherapp.core.common


fun Double.toCelsius(): String = "${this.toInt()}°C"

fun String.toWeatherIconUrl(): String =
    "https://openweathermap.org/img/wn/${this}@2x.png"

fun String.capitalizeWords(): String =
    this.split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }