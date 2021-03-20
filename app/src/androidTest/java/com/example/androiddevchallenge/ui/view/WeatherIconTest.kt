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

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.ui.theme.MyTheme
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class WeatherIconTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showIconTest() {
        // Start the app
        composeTestRule.setContent {
            MyTheme {
                WeatherIcon(
                    weather = Weather.Sunny,
                    dateTime = LocalDateTime.of(2021, 3, 20, 12, 0, 0),
                    modifier = Modifier.width(40.dp)
                )
            }
        }

        composeTestRule.onNode(hasTestTag("Weather Icon")).apply {
            assertIsDisplayed()
            assertWidthIsEqualTo(40.dp)
            assertHeightIsEqualTo(40.dp)
        }
    }
}
