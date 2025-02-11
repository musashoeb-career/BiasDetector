package com.example.learncompose.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class BiasDataViewModel(application: Application): AndroidViewModel(application) {


    val samsungHealth : SamsungHealthConnect = SamsungHealthConnect(application);
    val healthListener : HealthListenerService = HealthListenerService()

    val heartRate = healthListener.heartRateData
    val spo2Data =  healthListener.sp02Data

//    suspend fun startTest () {
//        samsungHealth.connectHealthService()
//        delay(11000)
//
//        if(samsungHealth.isConnected) {
//            samsungHealth.heartRateTracker.setEventListener(healthListener.heartRateListener)
//            samsungHealth.sp02Tracker.setEventListener(healthListener.heartRateListener)
//        }
//
//
//    }
//
//     fun EndTest () {
//            samsungHealth.sp02Tracker.unsetEventListener()
//            samsungHealth.heartRateTracker.unsetEventListener()
//            samsungHealth.disconnectHealthService()
//
//        }



}