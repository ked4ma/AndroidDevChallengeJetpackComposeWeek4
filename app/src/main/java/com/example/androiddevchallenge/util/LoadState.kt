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
package com.example.androiddevchallenge.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class LoadState<out T> {

    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val value: T) : LoadState<T>()
    data class Error(val e: Throwable? = null) : LoadState<Nothing>()

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isError(): Boolean {
        return this is Error
    }

    fun getValueOrNull(): T? {
        if (this is Loaded<T>) {
            return value
        }
        return null
    }

    fun getThrowableOrNull(): Throwable? {
        if (this is Error) {
            return e
        }
        return null
    }
}

@Suppress("USELESS_CAST")
fun <T> Flow<T?>.toLoadState(): Flow<LoadState<T>> =
    map {
        if (it == null) {
            LoadState.Error()
        } else {
            LoadState.Loaded(it) as LoadState<T>
        }
    }.onStart {
        emit(LoadState.Loading)
    }.catch { cause ->
        emit(LoadState.Error(cause))
    }
