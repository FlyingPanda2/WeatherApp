package com.pandoscorp.weatherapp.presentation.weather.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandoscorp.weatherapp.core.constants.Constants
import com.pandoscorp.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.pandoscorp.weatherapp.domain.usecase.GetWeatherUseCase
import com.pandoscorp.weatherapp.presentation.weather.state.WeatherState
import com.pandoscorp.weatherapp.core.common.AppResult
import com.pandoscorp.weatherapp.domain.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.pandoscorp.weatherapp.domain.usecase.GetCurrentLocationUseCase

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _weatherState = mutableStateOf<WeatherState>(WeatherState.Loading)
    val weatherState: State<WeatherState> = _weatherState

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadWeather(Constants.DEFAULT_CITY)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            when (val result = getWeatherUseCase(city)) {
                is AppResult.Success<*> -> {
                    _weatherState.value = WeatherState.Success(result.data as Weather)
                }
                is AppResult.Error -> {
                    _weatherState.value = WeatherState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun loadWeatherByLocation() {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            when (val locationResult = getCurrentLocationUseCase()) {
                is AppResult.Success -> {
                    val location = locationResult.data
                    when (val weatherResult = getWeatherByLocationUseCase(location.lat, location.lon)) {
                        is AppResult.Success<*> -> {
                            _weatherState.value = WeatherState.Success(weatherResult.data as Weather)
                        }
                        is AppResult.Error -> {
                            _weatherState.value = WeatherState.Error(weatherResult.message)
                        }
                        else -> {}
                    }
                }
                is AppResult.Error -> {
                    _weatherState.value = WeatherState.Error(locationResult.message)
                }
                else -> {}
            }
        }
    }
}