package com.example.learncompose.presentation.Oxymeter

import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learncompose.presentation.SamsungHealthConnect
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

class OxymeterViewModel(application: Application) : AndroidViewModel(application) {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("users/names")

    fun ReadDatabase() {

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called when data is retrieved from the database
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("Firebase", "Value is: $value")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // This method is called when the read operation is cancelled
                Log.w("Firebase", "Failed to read value.", databaseError.toException())
            }
    })
    }

    val healthConnect = SamsungHealthConnect(application)
    val sp02Listener = OxygenListenerService()

    suspend fun startTest() {

        healthConnect.connectHealthService()

        delay(2000)
        if(healthConnect.isConnected) {

            healthConnect.sp02Tracker.setEventListener(sp02Listener.sp02Listener)

        }


    }

    fun stopTest() {
        val spo2Final = sp02Listener.sp02Data.value
        myRef.setValue(spo2Final)
        healthConnect.sp02Tracker.unsetEventListener()
        healthConnect.disconnectHealthService()
    }

    }
