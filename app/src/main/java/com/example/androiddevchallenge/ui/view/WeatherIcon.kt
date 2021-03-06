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

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
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
        modifier = modifier.aspectRatio(1F).semantics {
            testTag = "Weather Icon"
        }
    )
}
