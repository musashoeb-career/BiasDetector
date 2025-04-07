package com.example.learncompose.presentation.Oxymeter

import android.app.Application
import android.util.Log
import androidx.collection.emptyIntList
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
    var myRef : DatabaseReference = database.getReference("")
    val healthConnect = SamsungHealthConnect(application)
    val sp02Listener = OxygenListenerService()




    fun updateReference( employeeID: String) {
        myRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests")
        myRef.orderByKey().limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        // Get the last child (most recent node)
                        val mostRecentNode = snapshot.children.last()
                       Log.d("Most Recent Node:", "Node: $mostRecentNode")
                        myRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests/${mostRecentNode.key}/sp02")
                        Log.d("Node Path", "EHTS/EHTS2025/employees/$employeeID/tests/${mostRecentNode.key}")
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

        sp02Listener._sp02Data.value = 0
        healthConnect.connectHealthService()

        delay(1000)
        if(healthConnect.isConnected) {
            healthConnect.sp02Tracker.setEventListener(sp02Listener.sp02Listener)

        }


    }

    fun stopTest() {
        val spo2Final = sp02Listener.sp02Data.value
        Log.d("Sp02 Final", "Data: $spo2Final")
        myRef.setValue(spo2Final).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Oxymeter Push", "Successful Oxi Push")
            }
            else {
                Log.d("Oxymeter Push", "Bad Oxi Push")
            }
        }

        healthConnect.sp02Tracker.unsetEventListener()
        healthConnect.disconnectHealthService()
    }

    }
