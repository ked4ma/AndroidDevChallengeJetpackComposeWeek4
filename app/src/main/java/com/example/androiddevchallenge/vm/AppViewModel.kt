package com.example.androiddevchallenge.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf


private val LocalWeatherViewModel = compositionLocalOf<WeatherViewModel> {
    error("WeatherViewModel is not provided")
}

@Composable
fun ProvideWeatherViewModel(viewModel: WeatherViewModel, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWeatherViewModel provides viewModel, content = content)
}

object AppViewModel {
    val weather: WeatherViewModel
        @Composable
        get() = LocalWeatherViewModel.current
}