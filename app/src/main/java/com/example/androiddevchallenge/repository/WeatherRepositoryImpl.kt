/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.api.DummyWeatherApi
import com.example.androiddevchallenge.api.WeatherApi
import com.example.androiddevchallenge.model.CurrentWeather
import com.example.androiddevchallenge.model.WeatherForecast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @DummyWeatherApi val weatherApi: WeatherApi
) : WeatherRepository {
    private val _currentData = Channel<CurrentWeather>(Channel.CONFLATED)
    override val currentData = flow {
        try {
            for (data in _currentData) {
                emit(data)
            }
        } finally {
            _currentData.close()
        }
    }

    private val _forecastData = Channel<WeatherForecast>(Channel.CONFLATED)
    override val forecastData: Flow<WeatherForecast> = flow {
        try {
            for (data in _forecastData) {
                emit(data)
            }
        } finally {
            _forecastData.close()
        }
    }

    override suspend fun refresh() {
        val data = weatherApi.getCurrentData().toModel()
        _currentData.send(data)

        val forecast = weatherApi.getForecast().toModel()
        _forecastData.send(forecast)
    }
}
