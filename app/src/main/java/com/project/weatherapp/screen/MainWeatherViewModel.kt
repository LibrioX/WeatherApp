package com.project.weatherapp.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weatherapp.data.db.WeatherLocal
import com.project.weatherapp.repository.WeatherRepository
import com.project.weatherapp.util.Const
import com.project.weatherapp.util.EnumCity
import com.project.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _weatherState: MutableStateFlow<List<WeatherLocal>> = MutableStateFlow(emptyList())
    val weatherState: MutableStateFlow<List<WeatherLocal>>
        get() = _weatherState

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading


    init {
        getWeatherLocal()
        enumValues<EnumCity>().forEach {
            getWeatherById(it.id)
        }
    }

    fun getWeather(lat: String, lon: String) {
        weatherRepository.getWeather(lat, lon, Const.apiKey)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        val data = result.data
                        if (data != null) {
                            weatherRepository.saveWeather(
                                WeatherLocal(
                                    id = Const.idCurrrentLocation,
                                    lat = data.coord.lat,
                                    lon = data.coord.lon,
                                    temperature = data.main.temp,
                                    weather = data.weather[0].main,
                                    weatherDescription = data.weather[0].description,
                                    humidity = data.main.humidity,
                                    windSpeed = data.wind.speed,
                                    pressure = data.main.pressure,
                                    icon = data.weather[0].icon,
                                    dateTime = data.dt
                                )
                            )
                            reverseGeocoding(data.coord.lat.toString(), data.coord.lon.toString())
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getWeatherById(id: Int) {
        weatherRepository.getWeatherById(id, Const.apiKey)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        val data = result.data
                        if (data != null) {
                            weatherRepository.saveWeather(
                                WeatherLocal(
                                    id = id,
                                    lat = data.coord.lat,
                                    lon = data.coord.lon,
                                    temperature = data.main.temp,
                                    weather = data.weather[0].main,
                                    weatherDescription = data.weather[0].description,
                                    humidity = data.main.humidity,
                                    windSpeed = data.wind.speed,
                                    pressure = data.main.pressure,
                                    icon = data.weather[0].icon,
                                    dateTime = data.dt
                                )
                            )
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun reverseGeocoding(lat: String, lon: String) {
        weatherRepository.reverseGeocoding(lat, lon, Const.apiKey)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        val data = result.data
                        if (data != null) {
                            var location = data[0].name
                            weatherRepository.updateLocation(Const.idCurrrentLocation, location)
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getWeatherLocal() {
        weatherRepository.getAllWeather()
            .onEach { result ->
                if (result.isNotEmpty())
                    _weatherState.value = result
            }.launchIn(viewModelScope)
    }
}
