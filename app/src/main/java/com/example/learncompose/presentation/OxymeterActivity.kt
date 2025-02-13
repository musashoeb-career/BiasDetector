package com.example.learncompose.presentation


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.learncompose.presentation.theme.WatchTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OxymeterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("users/names")

        // Set a value in the database
        myRef.setValue("Hello, Firebase!")

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
        // Permission check
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACTIVITY_RECOGNITION)
            == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }


        setContent {

//            val viewModel: BiasDataViewModel = viewModel()
//            val heartRate by viewModel.heartRate.collectAsStateWithLifecycle()
//            val sp02data by viewModel.spo2Data.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()
            val healthConnect : SamsungHealthConnect = SamsungHealthConnect(this@OxymeterActivity)
            val healthListener : HealthListenerService = HealthListenerService()
            val spO2Level by healthListener.sp02Data.collectAsState(initial = 0)

            // Start the test when the composable is first launched
            suspend fun startTest() {



                healthConnect.connectHealthService()

                delay(5000)
                if(healthConnect.isConnected) {

                    healthConnect.sp02Tracker.setEventListener(healthListener.sp02Listener)
                    // healthConnect.heartRateTracker.setEventListener(healthListener.sp02Listener)
                }


            }

            fun stopTest() {
                healthConnect.sp02Tracker.unsetEventListener()
                val spo2Final = spO2Level.toString()
                myRef.setValue(spo2Final)
                //healthConnect.heartRateTracker.unsetEventListener()
                healthConnect.disconnectHealthService()
            }

            // Composable content
            WearApp(
                onStartTestClick = {
                    scope.launch {
                        startTest()
                    }
                },
                onStopTestClick = {
                    stopTest()
                }
            )
        }
    }
}

@Composable
fun WearApp(onStartTestClick: () -> Unit,  onStopTestClick: () -> Unit) {
    WatchTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WatchTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {onStartTestClick()},
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text("Start")
            }
        }


        Button(onClick = {onStopTestClick()}) {
            Text("Stop")
        }


//        Button(
//            onClick = { /* Navigate action here */ },
//        ) {
//            Text("Navigate to Second Page")
//        }
    }
}
