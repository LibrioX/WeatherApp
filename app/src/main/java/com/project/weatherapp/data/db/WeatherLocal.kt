package com.project.weatherapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherLocal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val weather: String,
    val weatherDescription : String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val icon: String,
    val dateTime: Int,
    val location: String = ""
)