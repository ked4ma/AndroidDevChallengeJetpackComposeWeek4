package com.example.androiddevchallenge.api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    @Binds
    abstract fun bindWeatherApi(
        weatherApiImpl: WeatherApiImpl
    ): WeatherApi
}