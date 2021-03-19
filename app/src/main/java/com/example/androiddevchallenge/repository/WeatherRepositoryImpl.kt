package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.api.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    val weatherApi: WeatherApi
) : WeatherRepository {
}