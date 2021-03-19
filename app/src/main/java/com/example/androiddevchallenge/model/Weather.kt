package com.example.androiddevchallenge.model

import androidx.annotation.DrawableRes
import com.example.androiddevchallenge.R

enum class Weather(@DrawableRes val daytime: Int, @DrawableRes val nighttime: Int = daytime) {
    Sunny(R.drawable.ic_day_sunny, R.drawable.ic_night_clear),
    DayCloudy(R.drawable.ic_day_cloudy, R.drawable.ic_night_cloudy),
    Cloudy(R.drawable.ic_cloud),
    BrokenCloudy(R.drawable.ic_cloudy),
    ShowerRain(R.drawable.ic_day_showers, R.drawable.ic_night_showers),
    Rain(R.drawable.ic_rain),
    Thunderstorm(R.drawable.ic_thunderstorm),
    Snow(R.drawable.ic_snow),
    Mist(R.drawable.ic_day_fog, R.drawable.ic_night_fog),
    Unknown(R.drawable.ic_unknown),
}