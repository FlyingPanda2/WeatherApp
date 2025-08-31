package com.pandoscorp.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pandoscorp.weatherapp.core.common.capitalizeWords
import com.pandoscorp.weatherapp.core.common.toCelsius
import com.pandoscorp.weatherapp.core.common.toWeatherIconUrl
import com.pandoscorp.weatherapp.core.theme.CloudyGradient
import com.pandoscorp.weatherapp.core.theme.SunnyGradient
import com.pandoscorp.weatherapp.domain.model.Weather

@Composable
fun WeatherContent(
    weather: Weather,
    onRefresh: () -> Unit,
    onRequestLocation: () -> Unit
) {
    val gradientColors = remember(weather.weatherId) {
        when {
            weather.weatherId in 200..232 -> listOf(Color(0xFF4A6572), Color(0xFF344955))
            weather.weatherId in 300..321 -> listOf(Color(0xFF7BAFD4), Color(0xFF5D8CAE))
            weather.weatherId in 500..531 -> listOf(Color(0xFF5C6BC0), Color(0xFF3F51B5))
            weather.weatherId in 600..622 -> listOf(Color(0xFF90CAF9), Color(0xFF64B5F6))
            weather.weatherId in 701..781 -> listOf(Color(0xFF9E9E9E), Color(0xFF757575))
            weather.weatherId == 800 -> SunnyGradient
            weather.weatherId in 801..804 -> CloudyGradient
            else -> SunnyGradient
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${weather.city}, ${weather.country}",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = weather.icon.toWeatherIconUrl(),
                contentDescription = weather.description,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = weather.temperature.toCelsius(),
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = weather.description.capitalizeWords(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            WeatherDetails(weather)

            Spacer(modifier = Modifier.height(32.dp))

            ActionButtons(onRefresh, onRequestLocation)
        }
    }
}

@Composable
fun ActionButtons(onRefresh: () -> Unit, onRequestLocation: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = onRefresh,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Обновить", color = Color.White)
        }

        Button(
            onClick = onRequestLocation,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Мое местоположение", color = Color.White)
        }
    }
}