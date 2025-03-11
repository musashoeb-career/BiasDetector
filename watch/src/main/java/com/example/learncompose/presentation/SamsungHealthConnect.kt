package com.example.learncompose.presentation

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.samsung.android.service.health.tracking.ConnectionListener
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.HealthTrackerException
import com.samsung.android.service.health.tracking.HealthTrackingService
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.HealthTrackerType
import com.samsung.android.service.health.tracking.data.ValueKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/* In order to use services like heartRate and Spo2 without directly making calculations
off the sensor, we connect to a health platform that will run those services for us. In this
class, we're just managing a connection to said platform
* */

class SamsungHealthConnect(private val context: Context){


    var isConnected : Boolean = false
    private lateinit var service : HealthTrackingService
    lateinit var heartRateTracker : HealthTracker
    lateinit var sp02Tracker: HealthTracker


    //Once we are connected, lets make sure services, like Spo2, are available
    fun getAvailableTrackers () {

        if(isConnected) {
            val availableTrackers : MutableList<HealthTrackerType> =
                service.trackingCapability.supportHealthTrackerTypes

            for (tracker in availableTrackers) {
                Log.d("Tracker:",  "$tracker")
            }
        }

        else {
            Log.d("Connection Message","not connected!")
        }

    }

    /*Here's how we listen to the state of our connection. Whether it is successful,
    * ended, or failed, it will be logged here.*/

    private val connectionListener = object : ConnectionListener {
        override fun onConnectionSuccess() {
            Log.d("Connection Message", "Successfully Connected!")
            isConnected = true
            getAvailableTrackers()
            heartRateTracker = service.getHealthTracker(HealthTrackerType.HEART_RATE_CONTINUOUS)
            sp02Tracker = service.getHealthTracker(HealthTrackerType.SPO2_ON_DEMAND)

        }

        override fun onConnectionEnded() {
            Log.d("Connection Message","Connection Closed!")
            isConnected = false
        }

        override fun onConnectionFailed(p0: HealthTrackerException?) {
            Log.d("Connection Message", "Connection Unsuccessful")

            if (p0 != null) {

                if(p0.errorCode == HealthTrackerException.OLD_PLATFORM_VERSION
                    || p0.errorCode == HealthTrackerException.PACKAGE_NOT_INSTALLED) {
                    Toast.makeText(
                        context,
                        "Health Platform version is outdated or not installed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }

    }


    /*Here's what you run in main, this is you first connecting to the platform.
    * When you run this, the connection listener is attached and will update you on how
    * it goes*/

   fun connectHealthService() {

       service = HealthTrackingService(connectionListener, context)
       service.connectService()
   }



    fun disconnectHealthService() {
       if(isConnected) {
           service.disconnectService()
       }
   }



}




