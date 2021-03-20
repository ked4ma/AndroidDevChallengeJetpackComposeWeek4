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
package com.example.androiddevchallenge.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.model.WeatherForecast
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.Const
import com.example.androiddevchallenge.util.ShowState
import com.example.androiddevchallenge.vm.AppViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherForecastScreen(modifier: Modifier = Modifier) {
    val state by AppViewModel.weather.forecastState.collectAsState()

    Box(modifier = modifier) {
        Crossfade(state) { loadState ->
            when {
                loadState.isLoading() -> {
                }
                loadState.isError() -> Text("Error")
                else -> WeatherForecast(forecast = loadState.getValueOrNull() ?: WeatherForecast())
            }
        }
    }
}

@Composable
private fun WeatherForecast(
    modifier: Modifier = Modifier,
    forecast: WeatherForecast
) {
    val transitionState = remember { MutableTransitionState(ShowState.Hidden) }
    val transition = updateTransition(transitionState)
    transitionState.targetState = ShowState.Show

    val gaugeTransition by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 800)
        }
    ) { state ->
        when (state) {
            ShowState.Hidden -> 0F
            ShowState.Show -> 1F
        }
    }

    val (min, max) = forecast.list.map { it.temp.value }.let {
        (it.minOrNull() ?: 0F) to (it.maxOrNull() ?: 0F)
    }
    val percentBase = (max - min).coerceAtLeast(0.1F)

    val gaugeDradient = verticalGradient(MyTheme.domainColors.gaugeGradientColors)
    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            val width = size.width
            val height = size.height
            val heightPad = size.height / 4
            val num = forecast.list.size - 1
            val widthInterval = width / num

            fun calcHeight(value: Float): Float {
                val percent = (value - min) / percentBase
                return (heightPad + height / 2F * percent) * gaugeTransition
            }

            val path = Path().apply {
                // move to start point
                moveTo(0F, height)

                var w = 0F
                forecast.list.forEach { f ->
                    lineTo(w, height - calcHeight(f.temp.value))
                    w += widthInterval
                }

                relativeLineTo(width, 0F)
                lineTo(width, height)
                close()
            }
            drawPath(path, gaugeDradient)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            forecast.list.subList(1, forecast.list.lastIndex).forEach { f ->
                ForecastItem(
                    temperature = f.temp.value - Const.Kelvin,
                    weather = f.weather,
                    dateTime = f.dateTime
                )
            }
        }
    }
}

@Composable
private fun ForecastItem(
    modifier: Modifier = Modifier,
    temperature: Float,
    weather: Weather,
    dateTime: LocalDateTime
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.temperature_short, temperature.toInt()),
            style = MyTheme.typography.body2,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        WeatherIcon(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally),
            weather = weather, dateTime = dateTime,
        )
        Text(
            text = dateTime.format(
                DateTimeFormatter.ofPattern(stringResource(id = R.string.hour_min))
            ),
            style = MyTheme.typography.body2,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
    }
}
