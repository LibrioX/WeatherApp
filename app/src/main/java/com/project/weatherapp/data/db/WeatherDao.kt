package com.project.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherLocal)

    @Query("UPDATE WeatherLocal SET location = :location WHERE id = :id")
    suspend fun updateLocation(id: Int, location: String)

    @Query("DELETE FROM WeatherLocal")
    suspend fun deleteAllWeather() : Int

    @Query("SELECT * FROM WeatherLocal")
    fun getAllWeather(): Flow<List<WeatherLocal>>
}