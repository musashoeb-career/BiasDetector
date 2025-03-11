package com.example.learncompose.presentation.Oxymeter

import android.util.Log
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.ValueKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OxygenListenerService {

    private val _sp02Data = MutableStateFlow<Int>(0)
    val sp02Data = _sp02Data.asStateFlow()

    val sp02Listener = object : HealthTracker.TrackerEventListener {
        override fun onFlushCompleted() {
            Log.d("Sp02 Listener", "Oxygen Flush Completed")
        }

        override fun onDataReceived(dataPoints: MutableList<DataPoint>) {
            for (data in dataPoints) {

                val incomingOxygenData = data.getValue((ValueKey.SpO2Set.SPO2))
                if (incomingOxygenData != null) {
                    _sp02Data.value = incomingOxygenData
                    Log.d("Sp02 Listener", "Data Received: $incomingOxygenData")
                }

                else {
                    Log.d("Sp02 Listener", "Null Data: $incomingOxygenData")
                }

                }
            }


        override fun onError(p0: HealthTracker.TrackerError?) {
            Log.d("Oxygen Listener", "Error with Oxygen Listener $p0")
        }
    }
}