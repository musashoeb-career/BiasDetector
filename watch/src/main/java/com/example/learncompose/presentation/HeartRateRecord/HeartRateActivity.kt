package com.example.learncompose.presentation.HeartRateRecord

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.learncompose.presentation.LoginSetup.SelectUserActivity
import com.example.learncompose.presentation.Oxymeter.OxymeterViewModel
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme
import kotlinx.coroutines.launch

class HeartRateActivity : ComponentActivity() {

        private val viewModel: HeartRateViewModel by viewModels()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val employeeID = intent.getStringExtra("EMPLOYEEID")
            Log.d("Heart Rate Employee", "$employeeID")
            if (employeeID != null) {
                viewModel.updateReference(employeeID)
            }

            if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
            }

            fun navigateSelectUser() {
                val intent = Intent(this, SelectUserActivity::class.java)
                startActivity(intent)
            }

            setContent {
                val heartRateLevel by viewModel.heartRateListener.heartRateData.collectAsState(initial = emptyList())
                val scope = rememberCoroutineScope()
                // Composable content
                WatchTheme {
                    HeartRateView(
                        onStartTestClick = {
                            scope.launch {
                                viewModel.startTest()                                   }
                        },
                        onStopTestClick = {
                            viewModel.stopTest()
                        },
                        heartRateLevel = heartRateLevel,

                        onNavigateSelectActivity = {navigateSelectUser()}
                    )
                }
            }
        }
    }

    @Composable
    fun HeartRateView(onStartTestClick: () -> Unit, onStopTestClick: () -> Unit, heartRateLevel : List<Int>, onNavigateSelectActivity: () -> Unit) {

        val isMeasuring = remember { mutableStateOf(false) }

        Column (
            modifier = Modifier
                .fillMaxSize()  // Make the Column fill the entire screen
                .background(WatchTheme.colorScheme.background)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Align children horizontally in the center
            verticalArrangement = Arrangement.Center // Align children vertically in the center
        ){

            androidx.compose.material3.Text(text = "Heart Rate",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 23.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )

                androidx.compose.material3.Text( text = "${heartRateLevel.lastOrNull() ?: 0}",
                    style = TextStyle(
                        fontFamily = Jura,
                        fontSize = 45.sp,
                        color = WatchTheme.colorScheme.primary
                    )
                )




            if (isMeasuring.value) {
                androidx.wear.compose.material.Button(
                    onClick = { onStopTestClick()
                              isMeasuring.value = false},
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth(0.5f)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.primary)
                ) {
                    androidx.compose.material3.Text(
                        "Complete",
                        style = TextStyle(
                            fontFamily = Jura,
                            fontSize = 17.sp,
                            color = WatchTheme.colorScheme.secondary
                        )
                    )
                }
            }
            else {
                androidx.wear.compose.material.Button(
                    onClick = {
                        onStartTestClick()
                        isMeasuring.value = true
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth(0.5f)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.secondary)
                ) {
                    androidx.compose.material3.Text(
                        "Measure",
                        style = TextStyle(
                            fontFamily = Jura,
                            fontSize = 17.sp,
                            color = WatchTheme.colorScheme.primary
                        )
                    )
                }
            }

            Button(
                onClick = {onNavigateSelectActivity()},
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth(0.5f)
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.secondary)) {
                Text(
                    text = "Finish Test",
                    style = TextStyle(
                        fontFamily = Jura,
                        fontSize = 16.sp,
                        color = WatchTheme.colorScheme.primary
                    )
                )
            }


        }
    }
