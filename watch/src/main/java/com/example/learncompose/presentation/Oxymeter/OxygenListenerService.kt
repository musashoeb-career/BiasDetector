package com.example.learncompose.presentation.Oxymeter

import android.util.Log
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.ValueKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



class OxygenListenerService {

    val _sp02Data = MutableStateFlow<Int>(0)   // A flow variable to track the incoming oxygen data
    val sp02Data = _sp02Data.asStateFlow() // a variable that lets us read the flow


    /* This defines what to do when oxygen data is received after connecting to Samsung Health. Similar
    * to the listener class in SamsungHealthConnect, this oxygen listener gives us status updates on the data
    * we're collecting */

    val sp02Listener = object : HealthTracker.TrackerEventListener {

        //Done Reading? Log a message
        override fun onFlushCompleted() {
            Log.d("Sp02 Listener", "Oxygen Flush Completed")
        }

        // Everytime data is received, extract the value and compare it to previous stored values
        override fun onDataReceived(dataPoints: MutableList<DataPoint>) {
            for (data in dataPoints) {

                val incomingOxygenData = data.getValue((ValueKey.SpO2Set.SPO2))
                if (incomingOxygenData != null) {
                    Log.d("Sp02 Listener", "Data Received: $incomingOxygenData")
                    if (incomingOxygenData > _sp02Data.value) {
                        _sp02Data.value = incomingOxygenData
                    }

                }

                else {
                    Log.d("Sp02 Listener", "Null Data: $incomingOxygenData")
                }

                }
            }

        //Make sure to listen for errors as well
        override fun onError(p0: HealthTracker.TrackerError?) {
            Log.d("Oxygen Listener", "Error with Oxygen Listener $p0")
        }
    }
}