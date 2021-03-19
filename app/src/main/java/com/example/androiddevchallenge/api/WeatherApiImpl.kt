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
package com.example.androiddevchallenge.api

import com.example.androiddevchallenge.api.data.response.CurrentWeatherResponse
import com.example.androiddevchallenge.api.data.response.WeatherForecastResponse
import javax.inject.Inject

// TODO implement this class when use open api.
// Currently, this app suppose to use OpenWeatherMap
// ref. https://openweathermap.org
class WeatherApiImpl @Inject constructor() : WeatherApi {
    override suspend fun getCurrentData(): CurrentWeatherResponse {
        error("Not Implemented yet.")
    }

    override suspend fun getForecast(): WeatherForecastResponse {
        error("Not Implemented yet.")
    }
}
