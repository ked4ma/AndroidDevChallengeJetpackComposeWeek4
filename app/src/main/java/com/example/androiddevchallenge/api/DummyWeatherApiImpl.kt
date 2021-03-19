package com.example.androiddevchallenge.api

import com.example.androiddevchallenge.api.data.response.CurrentWeatherResponse
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DummyWeatherApiImpl @Inject constructor() : WeatherApi {
    override suspend fun getCurrentData(): CurrentWeatherResponse {
        delay(300) // delay for dummy
        return Json { ignoreUnknownKeys = true }.decodeFromString(DummyCurrentDataStr)
    }

    companion object {
        private const val DummyCurrentDataStr =
            "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":741,\"main\":\"Fog\",\"description\":\"fog\",\"icon\":\"50n\"}],\"base\":\"stations\",\"main\":{\"temp\":284.04,\"pressure\":1011,\"humidity\":93,\"tempmin\":280.93,\"tempmax\":287.04},\"visibility\":10000,\"wind\":{\"speed\":1.5},\"clouds\":{\"all\":20},\"dt\":1570234102,\"sys\":{\"type\":1,\"id\":1417,\"message\":0.0102,\"country\":\"GB\",\"sunrise\":1570255614,\"sunset\":1570296659},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}"
    }
}