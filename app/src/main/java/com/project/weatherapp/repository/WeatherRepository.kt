package com.project.weatherapp.repository

import com.project.weatherapp.data.api.ApiService
import com.project.weatherapp.data.db.WeatherDao
import com.project.weatherapp.data.db.WeatherLocal
import com.project.weatherapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(
    private val weatherApi: ApiService,
    private val weatherDao: WeatherDao
) {
    fun getWeather(lat: String, lon: String, apiKey: String) = flow {
        try {
            emit(Resource.Loading())
            val response = weatherApi.getWeather(lat, lon, apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error"))
        }
    }

    fun getWeatherById(id: Int, apiKey: String) = flow {
        try {
            emit(Resource.Loading())
            val response = weatherApi.getWeatherById(id, apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error"))
        }
    }

    fun reverseGeocoding(lat: String, lon: String, apiKey: String) = flow {
        try {
            emit(Resource.Loading())
            val response = weatherApi.reverseGeocoding(lat, lon, apiKey = apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error"))
        }
    }

    suspend fun updateLocation(id: Int, location: String) = weatherDao.updateLocation(id, location)

    fun getAllWeather()  = weatherDao.getAllWeather()

    suspend fun saveWeather(weather: WeatherLocal) = weatherDao.saveWeather(weather)

    suspend fun deleteAllWeather() = weatherDao.deleteAllWeather()
}