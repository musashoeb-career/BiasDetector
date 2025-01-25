package com.example.learncompose.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HeartRateViewModel: ViewModel() {

    private val _heartRate = MutableStateFlow(100)
    val heartRate: StateFlow<Int> = _heartRate
}