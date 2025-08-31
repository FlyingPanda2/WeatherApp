package com.pandoscorp.weatherapp.di

import android.content.Context
import com.pandoscorp.weatherapp.data.remote.api.WeatherApiService
import com.pandoscorp.weatherapp.data.remote.retrofit.RetrofitInstance
import com.pandoscorp.weatherapp.data.repository.LocationRepositoryImpl
import com.pandoscorp.weatherapp.data.repository.WeatherRepositoryImpl
import com.pandoscorp.weatherapp.domain.repository.LocationRepository
import com.pandoscorp.weatherapp.domain.repository.WeatherRepository
import com.pandoscorp.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.pandoscorp.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.pandoscorp.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWeatherByLocationUseCase(repository: WeatherRepository): GetWeatherByLocationUseCase {
        return GetWeatherByLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(context: Context): LocationRepository {
        return LocationRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(repository: LocationRepository): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(repository)
    }
}