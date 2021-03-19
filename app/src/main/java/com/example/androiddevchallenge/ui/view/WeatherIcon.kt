package com.example.androiddevchallenge.ui.view

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.androiddevchallenge.model.Weather
import java.time.LocalDateTime

@Composable
fun WeatherIcon(
    modifier: Modifier = Modifier,
    weather: Weather,
    dateTime: LocalDateTime
) {
    Icon(
        painter = painterResource(
            id = if (dateTime.hour in 5..18) {
                weather.daytime
            } else {
                weather.nighttime
            }
        ),
        contentDescription = "",
        modifier = modifier.aspectRatio(1F)
    )
}