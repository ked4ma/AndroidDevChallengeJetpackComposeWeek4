package com.example.androiddevchallenge.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class LoadState<out T> {

    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val value: T) : LoadState<T>()
    data class Error(val e: Throwable) : LoadState<Nothing>()

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
fun <T> Flow<T>.toLoadState(): Flow<LoadState<T>> =
    map { LoadState.Loaded(it) as LoadState<T> }
        .onStart { emit(LoadState.Loading) }
        .catch { cause -> emit(LoadState.Error(cause)) }
