package com.project.weatherapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.weatherapp.ui.components.CardWeather
import com.project.weatherapp.ui.components.MainCardWeather
import com.project.weatherapp.ui.components.NoLocation
import com.project.weatherapp.ui.components.SpinnerLoading
import com.project.weatherapp.util.Const.idCurrrentLocation

@Composable
fun MainWeatherPage(
    location: Pair<Double, Double>,
    permissionResult: Boolean,
    mainWeatherViewModel: MainWeatherViewModel = hiltViewModel()
) {
    val weatherState = mainWeatherViewModel.weatherState.collectAsState().value
    val isLoading by mainWeatherViewModel.isLoading.collectAsState()
    LaunchedEffect(key1 = location, key2 = permissionResult) {
        val (lat, lon) = location
        if (permissionResult && lat != 0.0 && lon != 0.0) {
            mainWeatherViewModel.getWeather(lat.toString(), lon.toString())
        }
    }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        if (weatherState.isNotEmpty()) {
            val weather = weatherState.find { it.id == idCurrrentLocation }
            val weatherCity = weatherState.filter { it.id != idCurrrentLocation }
            if (weather != null) {
                MainCardWeather(
                    temperature = weather.temperature,
                    weather = weather.weather,
                    weatherDescription = weather.weatherDescription,
                    humidity = weather.humidity,
                    windSpeed = weather.windSpeed,
                    pressure = weather.pressure,
                    icon = weather.icon,
                    dateTime = weather.dateTime,
                    location = weather.location
                )
            } else {
                NoLocation()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Other Locations",
                style = MaterialTheme.typography.titleLarge
            )
            LazyColumn {
                items(
                    items = weatherCity,
                    key = { it.id }
                ) {
                    CardWeather(
                        cityId = it.id,
                        temperature = it.temperature,
                        weather = it.weather,
                        weatherDescription = it.weatherDescription,
                        humidity = it.humidity,
                        windSpeed = it.windSpeed,
                        pressure = it.pressure,
                        icon = it.icon,
                        dateTime = it.dateTime
                    )
                }
            }
        } else {
            if (isLoading) {
                SpinnerLoading()
            }
        }
    }
}