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
package com.example.androiddevchallenge.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.repository.WeatherRepository
import com.example.androiddevchallenge.util.LoadState
import com.example.androiddevchallenge.util.toLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    val currentWeatherState = repository.currentData
        .toLoadState()
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)

    val forecastState = repository.forecastData
        .toLoadState()
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)

    var currentTime: LocalDateTime = LocalDateTime.now()
        private set

    init {
        viewModelScope.launch {
            repository.refresh()
        }
        currentTime = LocalDateTime.now()
    }
}
