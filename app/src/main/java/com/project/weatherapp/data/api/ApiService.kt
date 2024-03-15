package com.project.weatherapp.data.api

import com.project.weatherapp.data.model.ResponseGeocodingItem
import com.project.weatherapp.data.model.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat")
        lat : String,
        @Query("lon")
        lon : String,
        @Query("appid")
        apiKey : String,
        @Query("units")
        units : String = "metric"
    ): ResponseWeather

    @GET("data/2.5/weather")
    suspend fun getWeatherById(
        @Query("id")
        id : Int,
        @Query("appid")
        apiKey : String,
        @Query("units")
        units : String = "metric"
    ): ResponseWeather

    @GET("geo/1.0/reverse")
    suspend fun reverseGeocoding(
        @Query("lat")
        lat : String,
        @Query("lon")
        lon : String,
        @Query("limit")
        limit : Int = 1,
        @Query("appid")
        apiKey : String,
    ): List<ResponseGeocodingItem>

}