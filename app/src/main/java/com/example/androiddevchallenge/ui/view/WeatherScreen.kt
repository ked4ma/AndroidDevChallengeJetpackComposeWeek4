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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.CurrentWeather
import com.example.androiddevchallenge.model.Temperature
import com.example.androiddevchallenge.model.Wind
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.Const
import com.example.androiddevchallenge.util.ShowState
import com.example.androiddevchallenge.vm.AppViewModel
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.math.pow

@Composable
fun WeatherScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val state by AppViewModel.weather.currentWeatherState.collectAsState()

        Crossfade(state) { loadState ->
            when {
                loadState.isLoading() -> LoadingScreen(Modifier.align(Alignment.Center))
                loadState.isError() -> Text("Error")
                else -> ContentScreen(data = loadState.getValueOrNull() ?: CurrentWeather())
            }
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun ContentScreen(modifier: Modifier = Modifier, data: CurrentWeather) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val transitionState = remember { MutableTransitionState(ShowState.Hidden) }
        transitionState.targetState = ShowState.Show

        val transition = updateTransition(transitionState)
        val iconTransition by transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = 500)
            }
        ) { state ->
            when (state) {
                ShowState.Hidden -> 0F
                ShowState.Show -> 1F
            }
        }

        val iconGuide = createGuidelineFromStart(iconTransition / 2)
        val (icon, info, forecast) = createRefs()
        WeatherIcon(
            weather = data.weather,
            dateTime = AppViewModel.weather.currentTime,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    end.linkTo(iconGuide)
                    width = Dimension.percent(0.5F)
                }
                .alpha(iconTransition)
        )

        val infoTransition by transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = 500, delayMillis = 300)
            }
        ) { state ->
            when (state) {
                ShowState.Hidden -> 0F
                ShowState.Show -> 1F
            }
        }
        WeatherInfo(
            title = data.desc, temperature = data.temp, wind = data.wind,
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(info) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.5F)
                }
                .alpha(infoTransition)
        )

        WeatherForecastScreen(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(forecast) {
                    bottom.linkTo(parent.bottom)
                    height = Dimension.percent(0.5F)
                }
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun WeatherInfo(
    modifier: Modifier = Modifier,
    title: String,
    temperature: Temperature,
    wind: Wind,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MyTheme.typography.h3,
        )
        /**
         * calc temperature color roughly
         */
        fun temperatureColor(value: Float): Color {
            val v = value.coerceIn(-10F, 30F) + 10 // 0..40
            val r = (1F / 10) * (v - 30).coerceAtLeast(0F)
            val (g, b) = if (v >= 30) {
                1F - (1F / 10) * (v - 30) to 0F
            } else {
                val k = (v / 30F).pow(2)
                k to 1F - k
            }
            return Color(r, g, b)
        }
        Text(
            text = stringResource(R.string.temperature, (temperature.value - Const.Kelvin).toInt()),
            color = temperatureColor(temperature.value - Const.Kelvin),
            style = MyTheme.typography.h1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = stringResource(
                R.string.temperature_range,
                (temperature.max - Const.Kelvin).toInt(),
                (temperature.min - Const.Kelvin).toInt()
            ),
            style = MyTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_strong_wind),
                contentDescription = "",
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically),
            )
            Text(
                text = stringResource(R.string.wind_speed, wind.speed),
                style = MyTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            )
        }
    }
}
