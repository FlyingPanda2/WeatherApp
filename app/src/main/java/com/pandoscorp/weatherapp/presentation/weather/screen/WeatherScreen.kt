package com.pandoscorp.weatherapp.presentation.weather.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pandoscorp.weatherapp.core.constants.Constants
import com.pandoscorp.weatherapp.presentation.search.component.SearchBar
import com.pandoscorp.weatherapp.presentation.weather.component.ErrorScreen
import com.pandoscorp.weatherapp.presentation.weather.component.LoadingScreen
import com.pandoscorp.weatherapp.presentation.weather.component.WeatherContent
import com.pandoscorp.weatherapp.presentation.weather.state.WeatherState
import com.pandoscorp.weatherapp.presentation.weather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onRequestLocation: () -> Unit
) {
    val weatherState by viewModel.weatherState
    val searchQuery by viewModel.searchQuery

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = weatherState) {
            is WeatherState.Loading -> LoadingScreen()
            is WeatherState.Success -> WeatherContent(
                weather = state.weather,
                onRefresh = { viewModel.loadWeather(state.weather.city) },
                onRequestLocation = {viewModel.loadWeatherByLocation()}
            )
            is WeatherState.Error -> ErrorScreen(
                message = state.message,
                onRetry = { viewModel.loadWeather(Constants.DEFAULT_CITY) }
            )
        }

        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            onSearch = { viewModel.loadWeather(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        )
    }
}