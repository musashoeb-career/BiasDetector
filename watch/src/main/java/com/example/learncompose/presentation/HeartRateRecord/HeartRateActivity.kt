package com.example.learncompose.presentation.HeartRateRecord

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.learncompose.presentation.Oxymeter.OxymeterViewModel
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme
import kotlinx.coroutines.launch

class HeartRateActivity : ComponentActivity() {

        private val viewModel: HeartRateViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
            }

            setContent {
                val heartRateLevel by viewModel.heartRateListener.heartRateData.collectAsState(initial = emptyList())
                val scope = rememberCoroutineScope()
                // Composable content
                WatchTheme {
                    OxymeterView(
                        onStartTestClick = {
                            scope.launch {
                                viewModel.startTest()                                   }
                        },
                        onStopTestClick = {
                            viewModel.stopTest()
                        },
                        heartRateLevel = heartRateLevel
                    )
                }
            }
        }
    }

    @Composable
    fun OxymeterView(onStartTestClick: () -> Unit, onStopTestClick: () -> Unit, heartRateLevel : List<Int>) {


        Column (
            modifier = Modifier
                .fillMaxSize()  // Make the Column fill the entire screen
                .background(WatchTheme.colorScheme.background)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Align children horizontally in the center
            verticalArrangement = Arrangement.Center // Align children vertically in the center
        ){

            androidx.compose.material3.Text(text = "Heart Rate Level",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 25.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Text(text = "${heartRateLevel.lastOrNull()} %",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 40.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.wear.compose.material.Button(
                onClick = {
                    onStartTestClick()
                },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.4f)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.secondary)
            ) {
                androidx.compose.material3.Text(
                    "Measure",
                    style = TextStyle(
                        fontFamily = Jura,
                        fontSize = 15.sp,
                        color = WatchTheme.colorScheme.primary
                    )
                )
            }

            Spacer(Modifier.padding())

            androidx.wear.compose.material.Button(
                onClick = { onStopTestClick() },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(0.4f)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.tertiary)
            ) {
                androidx.compose.material3.Text(
                    "Complete",
                    style = TextStyle(
                        fontFamily = Jura,
                        fontSize = 15.sp,
                        color = WatchTheme.colorScheme.primary
                    )
                )
            }
        }
    }
