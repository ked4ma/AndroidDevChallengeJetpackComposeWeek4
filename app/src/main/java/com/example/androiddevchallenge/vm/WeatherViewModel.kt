package com.example.androiddevchallenge.vm

import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val repository: WeatherRepository
) : ViewModel() {
}