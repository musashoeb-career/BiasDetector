package com.example.learncompose.presentation.HeartRateRecord

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.learncompose.presentation.LoginSetup.SamsungHealthConnect
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay


class HeartRateViewModel(application: Application) : AndroidViewModel(application) {

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = database.getReference("")
        var timeRef : DatabaseReference = database.getReference("")
        val healthConnect = SamsungHealthConnect(application)
        val heartRateListener = HeartRateListenerService()

        var startTime : Long = 0;
        var endTime: Long = 0;


    fun updateReference( employeeID: String) {
        myRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests")
        myRef.orderByKey().limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        // Get the last child (most recent node)
                        val mostRecentNode = snapshot.children.last()
                        Log.d("Most Recent Node:", "Node: $mostRecentNode")
                        myRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests/${mostRecentNode.key}/heartRate")
                        timeRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests/${mostRecentNode.key}/totalTestTime")
                    } else {
                        Log.d("Most Recent Node:", "No Node Found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error appropriately, e.g., log it or display a message
                    Log.d("Error", "Error Retrieving Child Node")
                }
            })
    }




        suspend fun startTest() {

            heartRateListener._heartRateData.value = emptyList()

            //voiceRecorder.startRecording()
            healthConnect.connectHealthService()

            delay(1000)
            if(healthConnect.isConnected) {
                startTime = System.currentTimeMillis()
                healthConnect.heartRateTracker.setEventListener(heartRateListener.heartRateListener)

            }


        }

        fun stopTest() {

            val heartrateFinal = heartRateListener.heartRateData.value
            endTime = System.currentTimeMillis()
            val elapsedTime = (endTime - startTime) / 1000
            myRef.setValue(heartrateFinal)
            timeRef.setValue(elapsedTime)
            healthConnect.heartRateTracker.unsetEventListener()
            healthConnect.disconnectHealthService()
        }

    }

