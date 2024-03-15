package com.project.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.project.weatherapp.data.api.ApiConfig
import com.project.weatherapp.data.api.ApiService
import com.project.weatherapp.data.db.WeatherDao
import com.project.weatherapp.data.db.WeatherDatabase
import com.project.weatherapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService() : ApiService {
        return ApiConfig.getApiService()
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        apiService: ApiService,
        weatherDao : WeatherDao
    ) = WeatherRepository(apiService, weatherDao)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase)  = database.weatherDao()
}