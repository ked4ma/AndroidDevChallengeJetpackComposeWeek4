package com.example.androiddevchallenge.model

/**
 * Temperature data
 * each data represented with Kelvin.
 */
data class Temperature(
    val value: Float = 0F,
    val max: Float = 0F,
    val min: Float = 0F,
)