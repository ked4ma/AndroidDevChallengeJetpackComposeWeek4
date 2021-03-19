package com.example.androiddevchallenge.model

data class CurrentWeather(
    val name: String = "",
    val desc: String = "",
    val weather: Weather = Weather.Unknown,
    val temp: Temperature = Temperature(),
    val wind: Wind = Wind(),
)