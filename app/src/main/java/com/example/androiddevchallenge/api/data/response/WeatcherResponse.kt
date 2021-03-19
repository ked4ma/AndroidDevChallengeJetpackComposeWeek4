package com.example.androiddevchallenge.api.data.response

import com.example.androiddevchallenge.model.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.androiddevchallenge.model.Weather as ModelWeather
import com.example.androiddevchallenge.model.Temperature as ModelTemp
import com.example.androiddevchallenge.model.Wind as ModelWind

@Serializable
data class Weather(
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class Temperature(
    val temp: Float, // current temp
    @SerialName("tempmin") val tempMin: Float,
    @SerialName("tempmax") val tempMax: Float,
)

@Serializable
data class Wind(
    val speed: Float,
)

@Serializable
data class CurrentWeatherResponse(
    val weather: List<Weather>,
    @SerialName("main")
    val temp: Temperature,
    val wind: Wind,
) {
    fun toModel() = CurrentWeather(
        name = weather.first().main,
        desc = weather.first().description,
        weather = iconToWeather(weather.first().icon),
        temp = ModelTemp(value = temp.temp, max = temp.tempMax, min = temp.tempMin),
        wind = ModelWind(wind.speed),
    )

    private fun iconToWeather(icon: String) = when (icon) {
        "01d", "01n" -> ModelWeather.Sunny
        "02d", "02n" -> ModelWeather.DayCloudy
        "03d", "03n" -> ModelWeather.Cloudy
        "04d", "04n" -> ModelWeather.BrokenCloudy
        "09d", "09n" -> ModelWeather.ShowerRain
        "10d", "10n" -> ModelWeather.Rain
        "11d", "11n" -> ModelWeather.Thunderstorm
        "13d", "13n" -> ModelWeather.Snow
        "50d", "50n" -> ModelWeather.Mist
        else -> ModelWeather.Unknown
    }
}
