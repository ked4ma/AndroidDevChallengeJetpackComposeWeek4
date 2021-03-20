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
import com.example.androiddevchallenge.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @DummyWeatherApi val weatherApi: WeatherApi
) : WeatherRepository {
    private val _weatherInfo = MutableStateFlow(System.currentTimeMillis())

    override val weatherInfo = _weatherInfo.map {
        try {
            val current = weatherApi.getCurrentData().toModel()
            val forecast = weatherApi.getForecast().toModel()
            WeatherInfo(
                current,
                forecast,
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun refresh() {
        _weatherInfo.emit(System.currentTimeMillis())
    }
}
