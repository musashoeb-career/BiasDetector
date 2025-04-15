package com.example.learncompose.presentation.LoginSetup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.learncompose.R
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme

/* A simple landing page that navigates to the select user activity*/

class StartingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun navigateGetStarted() {
            val intent = Intent(this, SelectUserActivity::class.java)
            startActivity(intent)
        }

        setContent {
            WatchTheme {
                GetStarted(
                    onNavigateGetStarted = {
                        navigateGetStarted()
                    }
                )
            }
        }
    }
}

@Composable
fun GetStarted(onNavigateGetStarted: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WatchTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.heart_icon),
            contentDescription = "white heart logo",
            tint = Color.White,
            modifier = Modifier
                .size(60.dp))


        Text(
            text = "EHTS",
            style = TextStyle(
                fontFamily = Jura,
                fontSize = 20.sp,
                color = WatchTheme.colorScheme.primary
            )
        )

        Text(
           text = "Bias Detector",
            style = TextStyle(
                fontFamily = Jura,
                fontSize = 25.sp,
                color = WatchTheme.colorScheme.primary
            )
        )


        Button(
            onClick = { onNavigateGetStarted() },
            modifier = Modifier.fillMaxWidth(0.65f),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = WatchTheme.colorScheme.onBackground,
                containerColor = WatchTheme.colorScheme.onBackground
            )
        ) {
            Text(
                "Get Started",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 15.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )
        }
    }

}

