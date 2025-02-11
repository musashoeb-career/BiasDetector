package com.example.learncompose.presentation

import android.util.Log
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.ValueKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HealthListenerService() {

    private val _heartRateData = MutableStateFlow<List<Int>>(emptyList())
    val heartRateData = _heartRateData.asStateFlow()

    private val _sp02Data = MutableStateFlow<Int>(0)
    val sp02Data = _sp02Data.asStateFlow()


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

     val sp02Listener = object : HealthTracker.TrackerEventListener {
        override fun onFlushCompleted() {
            Log.d("Sp02 Listener", "Oxygen Flush Completed")
        }

        override fun onDataReceived(dataPoints: MutableList<DataPoint>) {
            for (data in dataPoints) {
                val status: Int = data.getValue<Int>(ValueKey.SpO2Set.STATUS)
                if (status == 2) {
                    val incomingOxygenData = dataPoints.mapNotNull {
                       data -> data.getValue((ValueKey.SpO2Set.SPO2))}.last()
                    _sp02Data.value = incomingOxygenData
                  Log.d("Sp02 Listener", "Data Received: $incomingOxygenData")

                }
            }
        }

        override fun onError(p0: HealthTracker.TrackerError?) {
            Log.d("Oxygen Listener", "Error with Oxygen Listener $p0")
        }
    }


}

