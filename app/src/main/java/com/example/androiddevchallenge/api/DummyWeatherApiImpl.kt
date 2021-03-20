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
package com.example.androiddevchallenge.api

import com.example.androiddevchallenge.api.data.response.CurrentWeatherResponse
import com.example.androiddevchallenge.api.data.response.LocalDateTimeSerializer
import com.example.androiddevchallenge.api.data.response.WeatherForecastResponse
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.random.Random

class DummyWeatherApiImpl @Inject constructor() : WeatherApi {
    override suspend fun getCurrentData(): CurrentWeatherResponse {
        delay(300) // delay for dummy
        if (Random(System.currentTimeMillis()).nextBoolean()) {
            throw RuntimeException("dummy exception")
        }
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(LocalDateTimeSerializer)
            }
        }.decodeFromString(DummyCurrentDataStr)
    }

    override suspend fun getForecast(): WeatherForecastResponse {
        delay(500) // delay for dummy
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(LocalDateTimeSerializer)
            }
        }.decodeFromString<WeatherForecastResponse>(DummyForecastDataStr).let {
            val now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
            it.copy(
                list = it.list.mapIndexed { index, f ->
                    f.copy(dt = now.plusHours(index * 3L))
                }
            )
        }
    }

    companion object {
        private const val DummyCurrentDataStr =
            "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":741,\"main\":\"Fog\",\"description\":\"fog\",\"icon\":\"50n\"}],\"base\":\"stations\",\"main\":{\"temp\":284.04,\"pressure\":1011,\"humidity\":93,\"tempmin\":280.93,\"tempmax\":287.04},\"visibility\":10000,\"wind\":{\"speed\":1.5},\"clouds\":{\"all\":20},\"dt\":1570234102,\"sys\":{\"type\":1,\"id\":1417,\"message\":0.0102,\"country\":\"GB\",\"sunrise\":1570255614,\"sunset\":1570296659},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}"
        private const val DummyForecastDataStr =
            "{\"cod\":\"200\",\"message\":0.011,\"cnt\":40,\"list\":[{\"dt\":1570244400,\"main\":{\"temp\":288.11,\"temp_min\":286.961,\"temp_max\":288.11,\"pressure\":1019.73,\"sea_level\":1019.73,\"grnd_level\":1011.77,\"humidity\":68,\"temp_kf\":1.15},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.15,\"deg\":275.935},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-05 03:00:00\"},{\"dt\":1570255200,\"main\":{\"temp\":287.37,\"temp_min\":286.509,\"temp_max\":287.37,\"pressure\":1020.2,\"sea_level\":1020.2,\"grnd_level\":1012.28,\"humidity\":70,\"temp_kf\":0.86},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.18,\"deg\":332.266},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-05 06:00:00\"},{\"dt\":1570266000,\"main\":{\"temp\":286.85,\"temp_min\":286.274,\"temp_max\":286.85,\"pressure\":1019.14,\"sea_level\":1019.14,\"grnd_level\":1011.47,\"humidity\":69,\"temp_kf\":0.58},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.28,\"deg\":308.346},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-05 09:00:00\"},{\"dt\":1570276800,\"main\":{\"temp\":286.22,\"temp_min\":285.936,\"temp_max\":286.22,\"pressure\":1019.05,\"sea_level\":1019.05,\"grnd_level\":1011.59,\"humidity\":66,\"temp_kf\":0.29},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.87,\"deg\":337.999},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-05 12:00:00\"},{\"dt\":1570287600,\"main\":{\"temp\":286.6,\"temp_min\":286.6,\"temp_max\":286.6,\"pressure\":1019.34,\"sea_level\":1019.34,\"grnd_level\":1011.91,\"humidity\":61,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.77,\"deg\":354.045},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-05 15:00:00\"},{\"dt\":1570298400,\"main\":{\"temp\":290.144,\"temp_min\":290.144,\"temp_max\":290.144,\"pressure\":1019.47,\"sea_level\":1019.47,\"grnd_level\":1012.38,\"humidity\":48,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.84,\"deg\":63.477},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-05 18:00:00\"},{\"dt\":1570309200,\"main\":{\"temp\":292.716,\"temp_min\":292.716,\"temp_max\":292.716,\"pressure\":1017.81,\"sea_level\":1017.81,\"grnd_level\":1010.57,\"humidity\":48,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.77,\"deg\":194.975},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-05 21:00:00\"},{\"dt\":1570320000,\"main\":{\"temp\":294.912,\"temp_min\":294.912,\"temp_max\":294.912,\"pressure\":1016.08,\"sea_level\":1016.08,\"grnd_level\":1008.57,\"humidity\":40,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.78,\"deg\":261.529},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-06 00:00:00\"},{\"dt\":1570330800,\"main\":{\"temp\":290.037,\"temp_min\":290.037,\"temp_max\":290.037,\"pressure\":1016.04,\"sea_level\":1016.04,\"grnd_level\":1008.56,\"humidity\":46,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.88,\"deg\":297.892},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-06 03:00:00\"},{\"dt\":1570341600,\"main\":{\"temp\":289.492,\"temp_min\":289.492,\"temp_max\":289.492,\"pressure\":1016.23,\"sea_level\":1016.23,\"grnd_level\":1008.63,\"humidity\":52,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.19,\"deg\":286.865},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-06 06:00:00\"},{\"dt\":1570352400,\"main\":{\"temp\":288.426,\"temp_min\":288.426,\"temp_max\":288.426,\"pressure\":1015.36,\"sea_level\":1015.36,\"grnd_level\":1008.26,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.64,\"deg\":354.121},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-06 09:00:00\"},{\"dt\":1570363200,\"main\":{\"temp\":288.443,\"temp_min\":288.443,\"temp_max\":288.443,\"pressure\":1015.02,\"sea_level\":1015.02,\"grnd_level\":1007.94,\"humidity\":50,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.64,\"deg\":2.511},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-06 12:00:00\"},{\"dt\":1570374000,\"main\":{\"temp\":288.502,\"temp_min\":288.502,\"temp_max\":288.502,\"pressure\":1015.78,\"sea_level\":1015.78,\"grnd_level\":1008.86,\"humidity\":48,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.7,\"deg\":22.151},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-06 15:00:00\"},{\"dt\":1570384800,\"main\":{\"temp\":290.429,\"temp_min\":290.429,\"temp_max\":290.429,\"pressure\":1016.54,\"sea_level\":1016.54,\"grnd_level\":1009.99,\"humidity\":47,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.04,\"deg\":57.533},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-06 18:00:00\"},{\"dt\":1570395600,\"main\":{\"temp\":294.239,\"temp_min\":294.239,\"temp_max\":294.239,\"pressure\":1015.26,\"sea_level\":1015.26,\"grnd_level\":1008.06,\"humidity\":45,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":0.76,\"deg\":111.548},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-06 21:00:00\"},{\"dt\":1570406400,\"main\":{\"temp\":295.666,\"temp_min\":295.666,\"temp_max\":295.666,\"pressure\":1013.98,\"sea_level\":1013.98,\"grnd_level\":1006.37,\"humidity\":37,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":2.35,\"deg\":237.557},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-07 00:00:00\"},{\"dt\":1570417200,\"main\":{\"temp\":290.533,\"temp_min\":290.533,\"temp_max\":290.533,\"pressure\":1014.86,\"sea_level\":1014.86,\"grnd_level\":1007.32,\"humidity\":49,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":2.48,\"deg\":288.289},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-07 03:00:00\"},{\"dt\":1570428000,\"main\":{\"temp\":289.4,\"temp_min\":289.4,\"temp_max\":289.4,\"pressure\":1015.67,\"sea_level\":1015.67,\"grnd_level\":1008.02,\"humidity\":52,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":1.12,\"deg\":325.428},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-07 06:00:00\"},{\"dt\":1570438800,\"main\":{\"temp\":288.752,\"temp_min\":288.752,\"temp_max\":288.752,\"pressure\":1014.96,\"sea_level\":1014.96,\"grnd_level\":1007.13,\"humidity\":55,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.31,\"deg\":236.207},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-07 09:00:00\"},{\"dt\":1570449600,\"main\":{\"temp\":288.207,\"temp_min\":288.207,\"temp_max\":288.207,\"pressure\":1014.68,\"sea_level\":1014.68,\"grnd_level\":1007.01,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.93,\"deg\":3.071},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-07 12:00:00\"},{\"dt\":1570460400,\"main\":{\"temp\":288.8,\"temp_min\":288.8,\"temp_max\":288.8,\"pressure\":1014.89,\"sea_level\":1014.89,\"grnd_level\":1007.36,\"humidity\":51,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.63,\"deg\":353.18},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-07 15:00:00\"},{\"dt\":1570471200,\"main\":{\"temp\":291.7,\"temp_min\":291.7,\"temp_max\":291.7,\"pressure\":1014.79,\"sea_level\":1014.79,\"grnd_level\":1007.64,\"humidity\":46,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.71,\"deg\":54.808},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-07 18:00:00\"},{\"dt\":1570482000,\"main\":{\"temp\":294.58,\"temp_min\":294.58,\"temp_max\":294.58,\"pressure\":1013.21,\"sea_level\":1013.21,\"grnd_level\":1005.87,\"humidity\":45,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.74,\"deg\":176.656},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-07 21:00:00\"},{\"dt\":1570492800,\"main\":{\"temp\":296.074,\"temp_min\":296.074,\"temp_max\":296.074,\"pressure\":1011.58,\"sea_level\":1011.58,\"grnd_level\":1003.76,\"humidity\":36,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.79,\"deg\":221.133},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-08 00:00:00\"},{\"dt\":1570503600,\"main\":{\"temp\":289.79,\"temp_min\":289.79,\"temp_max\":289.79,\"pressure\":1011.88,\"sea_level\":1011.88,\"grnd_level\":1004.04,\"humidity\":50,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":38},\"wind\":{\"speed\":1.98,\"deg\":266.458},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-08 03:00:00\"},{\"dt\":1570514400,\"main\":{\"temp\":288.46,\"temp_min\":288.46,\"temp_max\":288.46,\"pressure\":1012.33,\"sea_level\":1012.33,\"grnd_level\":1004.59,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":21},\"wind\":{\"speed\":0.96,\"deg\":244.745},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-08 06:00:00\"},{\"dt\":1570525200,\"main\":{\"temp\":287.929,\"temp_min\":287.929,\"temp_max\":287.929,\"pressure\":1011.21,\"sea_level\":1011.21,\"grnd_level\":1003.49,\"humidity\":59,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":63},\"wind\":{\"speed\":1.95,\"deg\":219.888},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-08 09:00:00\"},{\"dt\":1570536000,\"main\":{\"temp\":287.656,\"temp_min\":287.656,\"temp_max\":287.656,\"pressure\":1010.89,\"sea_level\":1010.89,\"grnd_level\":1002.98,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":82},\"wind\":{\"speed\":2.08,\"deg\":224.024},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-08 12:00:00\"},{\"dt\":1570546800,\"main\":{\"temp\":287.8,\"temp_min\":287.8,\"temp_max\":287.8,\"pressure\":1010.8,\"sea_level\":1010.8,\"grnd_level\":1003.06,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.47,\"deg\":245.923},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-08 15:00:00\"},{\"dt\":1570557600,\"main\":{\"temp\":291.6,\"temp_min\":291.6,\"temp_max\":291.6,\"pressure\":1011.23,\"sea_level\":1011.23,\"grnd_level\":1003.77,\"humidity\":56,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.52,\"deg\":186.719},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-08 18:00:00\"},{\"dt\":1570568400,\"main\":{\"temp\":293.558,\"temp_min\":293.558,\"temp_max\":293.558,\"pressure\":1009.9,\"sea_level\":1009.9,\"grnd_level\":1002.19,\"humidity\":50,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":86},\"wind\":{\"speed\":4.28,\"deg\":203.255},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-08 21:00:00\"},{\"dt\":1570579200,\"main\":{\"temp\":292.061,\"temp_min\":292.061,\"temp_max\":292.061,\"pressure\":1008.79,\"sea_level\":1008.79,\"grnd_level\":1000.93,\"humidity\":49,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":4.59,\"deg\":228.075},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-09 00:00:00\"},{\"dt\":1570590000,\"main\":{\"temp\":287.253,\"temp_min\":287.253,\"temp_max\":287.253,\"pressure\":1009.56,\"sea_level\":1009.56,\"grnd_level\":1001.22,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.89,\"deg\":228.195},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-09 03:00:00\"},{\"dt\":1570600800,\"main\":{\"temp\":286.892,\"temp_min\":286.892,\"temp_max\":286.892,\"pressure\":1010.62,\"sea_level\":1010.62,\"grnd_level\":1002.25,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.19,\"deg\":227.408},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-09 06:00:00\"},{\"dt\":1570611600,\"main\":{\"temp\":286,\"temp_min\":286,\"temp_max\":286,\"pressure\":1010.19,\"sea_level\":1010.19,\"grnd_level\":1001.99,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.61,\"deg\":245.123},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-09 09:00:00\"},{\"dt\":1570622400,\"main\":{\"temp\":285.364,\"temp_min\":285.364,\"temp_max\":285.364,\"pressure\":1010.75,\"sea_level\":1010.75,\"grnd_level\":1002.96,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.91,\"deg\":286.179},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-09 12:00:00\"},{\"dt\":1570633200,\"main\":{\"temp\":285.5,\"temp_min\":285.5,\"temp_max\":285.5,\"pressure\":1011.15,\"sea_level\":1011.15,\"grnd_level\":1003.72,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.16,\"deg\":290.839},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-09 15:00:00\"},{\"dt\":1570644000,\"main\":{\"temp\":289.085,\"temp_min\":289.085,\"temp_max\":289.085,\"pressure\":1011.62,\"sea_level\":1011.62,\"grnd_level\":1004.69,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.57,\"deg\":123.983},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-09 18:00:00\"},{\"dt\":1570654800,\"main\":{\"temp\":292.347,\"temp_min\":292.347,\"temp_max\":292.347,\"pressure\":1010.93,\"sea_level\":1010.93,\"grnd_level\":1003.7,\"humidity\":55,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.53,\"deg\":200.845},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-10-09 21:00:00\"},{\"dt\":1570665600,\"main\":{\"temp\":291.363,\"temp_min\":291.363,\"temp_max\":291.363,\"pressure\":1009.63,\"sea_level\":1009.63,\"grnd_level\":1002.27,\"humidity\":52,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.81,\"deg\":236.819},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-10-10 00:00:00\"}],\"city\":{\"id\":5391959,\"name\":\"San Francisco\",\"coord\":{\"lat\":37.7793,\"lon\":-122.4193},\"country\":\"US\",\"population\":805235,\"timezone\":-25200,\"sunrise\":1570198059,\"sunset\":1570240137}}"
    }
}
