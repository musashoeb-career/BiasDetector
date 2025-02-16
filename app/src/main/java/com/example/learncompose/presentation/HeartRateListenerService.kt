package com.example.learncompose.presentation

import android.util.Log
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.ValueKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HeartRateListenerService() {

    private val _heartRateData = MutableStateFlow<List<Int>>(emptyList())
    val heartRateData = _heartRateData.asStateFlow()

     val heartRateListener = object : HealthTracker.TrackerEventListener {
        override fun onFlushCompleted() {
            Log.d("Heart Rate Listener", "Flush Completed")
        }

        override fun onDataReceived(dataPoints: MutableList<DataPoint>) {
            val incomingHeartRate = dataPoints.mapNotNull {
                DataPoint -> DataPoint.getValue((ValueKey.HeartRateSet.HEART_RATE))
            }
            _heartRateData.value = incomingHeartRate
            Log.d("Heart Rate Listener", "Data Received: $_heartRateData")
        }

        override fun onError(p0: HealthTracker.TrackerError?) {
            Log.d("Heart Rate Listener", "Heart Listener Error $p0")
        }
    }




}

