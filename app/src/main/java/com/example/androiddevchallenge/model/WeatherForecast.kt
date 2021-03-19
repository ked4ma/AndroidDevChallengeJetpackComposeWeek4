package com.example.androiddevchallenge.model

import java.time.LocalDateTime

data class WeatherForecast(
    val list: List<Forecast> = emptyList()
)

data class Forecast(
    val name: String = "",
    val desc: String = "",
    val weather: Weather = Weather.Unknown,
    val temp: Temperature = Temperature(),
    val wind: Wind = Wind(),
    val dateTime: LocalDateTime = LocalDateTime.now()
)
