package com.example.learncompose.presentation.Oxymeter

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


/* Viewmodels act as a bridge between services classes and activity classes. You're collecting oxygen and heartrate
* in service classes, and displaying UI in activity classes.*/

class OxymeterViewModel(application: Application) : AndroidViewModel(application) {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef : DatabaseReference = database.getReference("")
    val healthConnect = SamsungHealthConnect(application)  //Create instances of the service classes we need
    val sp02Listener = OxygenListenerService()



    /*This function is called when navigating to the oxymeter page. Based on the user you selected, a reference
    * needs to be updated and a test needs to be created*/

    fun updateReference( employeeID: String) {
        myRef = database.getReference("EHTS/EHTS2025/employees/$employeeID/tests") //based on the employee you selected
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

    // these start and stop functions are what we give to UI, the "Measure" and "Complete" buttons
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
        Log.d("Oxygen Ref Pushed", "$myRef")

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
