package com.example.androiddevchallenge.model

data class CurrentWeather(
    val name: String,
    val desc: String,
    val weather: Weather,
    val temp: Temperature,
    val wind: Wind,
)