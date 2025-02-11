package com.example.learncompose.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.learncompose.presentation.theme.WatchTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

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
            val healthConnect : SamsungHealthConnect = SamsungHealthConnect(this@MainActivity)
            val healthListener : HealthListenerService = HealthListenerService()
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
               onClick = {onStartTestClick()}
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
