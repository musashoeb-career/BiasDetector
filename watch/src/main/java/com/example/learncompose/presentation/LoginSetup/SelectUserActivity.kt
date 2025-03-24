package com.example.learncompose.presentation.LoginSetup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.learncompose.presentation.Oxymeter.OxymeterActivity
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme

class SelectUserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val employeeListState = mutableStateOf<List<Employee>>(emptyList())
        val usersInstance = RetreiveUsersService(employeeListState)

        usersInstance.getUsers()

        // Define the navigate function
        fun navigateOxymeter(currentUser: String) {
            usersInstance.createUserTest(currentUser)
            val intent = Intent(this, OxymeterActivity::class.java)
            intent.putExtra("EMPLOYEEID", currentUser)
            startActivity(intent)
        }

        setContent {
            WatchTheme {
                DisplayUsers(employeeListState.value,
                    onNavigateOxymeter = { currentUser ->
                        navigateOxymeter(currentUser) // Pass the selected currentUser to the navigate function
                    })
            }
        }
    }
}




@Composable
fun DisplayUsers(mutableList: List<Employee>, onNavigateOxymeter: (String) -> Unit) {
    val currentUser = remember { mutableStateOf("") } // Track the selected current user

    if (mutableList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WatchTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Loading...",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 30.sp,
                    color = WatchTheme.colorScheme.primary
                )
            )
        }
    } else {
        val pagerState = rememberPagerState(pageCount = { mutableList.size })

        // Update currentUser dynamically when page changes
        LaunchedEffect(pagerState.currentPage) {
            currentUser.value = mutableList[pagerState.currentPage].employeeID
            // Set the current page's name
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WatchTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .background(WatchTheme.colorScheme.background)
            ) { page ->
                Text(
                    text = mutableList[page].fullName,
                    style = TextStyle(
                        fontFamily = Jura,
                        fontSize = 25.sp,
                        color = WatchTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        WatchTheme.colorScheme.onBackground
                    } else {
                        Color.LightGray
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { onNavigateOxymeter(currentUser.value) }, // Pass currentUser when the button is clicked
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = WatchTheme.colorScheme.onBackground,
                    containerColor = WatchTheme.colorScheme.onBackground
                )
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

        }
    }
}
