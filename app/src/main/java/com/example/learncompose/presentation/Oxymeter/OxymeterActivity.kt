package com.example.learncompose.presentation.Oxymeter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels // Use this to inject the ViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.wear.compose.material.ButtonDefaults
import com.example.learncompose.presentation.SamsungHealthConnect
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OxymeterActivity : ComponentActivity() {

    private val viewModel: OxymeterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACTIVITY_RECOGNITION)
            == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }

        setContent {
            val sp02Level by viewModel.sp02Listener.sp02Data.collectAsState(initial = 0)
            val scope = rememberCoroutineScope()
            // Composable content
            WatchTheme {
                WearApp(
                    onStartTestClick = {
                        scope.launch {
                            viewModel.startTest()                                   }
                    },
                    onStopTestClick = {
                        viewModel.stopTest()
                    },
                    sp02Level = sp02Level
                )
            }
        }
    }
}

@Composable
fun WearApp(onStartTestClick: () -> Unit, onStopTestClick: () -> Unit, sp02Level: Int) {

    Text(text = "Sp02 Level: $sp02Level")

    Row {
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
            Text(
                "Start Test",
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
            colors = ButtonDefaults.buttonColors(backgroundColor = WatchTheme.colorScheme.secondary)
        ) {
            Text(
                "Stop Test",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 15.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )
        }
    }
}
