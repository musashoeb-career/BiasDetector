package com.example.learncompose.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.learncompose.presentation.Oxymeter.OxymeterActivity
import com.example.learncompose.presentation.theme.Jura
import com.example.learncompose.presentation.theme.WatchTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)


        setContent {
            WatchTheme {
                LoginApp(onNavigate = { username, password -> onLogin(username, password) })
            }


        }


    }

    private fun navigateOxymeter(user : FirebaseUser) {
        val intent = Intent(this, OxymeterActivity::class.java)
        intent.putExtra("USERNAME",user.email)
        startActivity(intent)

    }

    private fun onLogin(username: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Firebase Auth", "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        navigateOxymeter(user)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Firebase Auth", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }
}

@Composable
fun LoginApp(onNavigate: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()  // Make the Column fill the entire screen
            .background(WatchTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally, // Align children horizontally in the center
        verticalArrangement = Arrangement.Center // Align children vertically in the center
    ) {

        Text(
            text = "Web Auth",
            style = TextStyle(
                fontFamily = Jura,
                fontSize = 24.sp,
                color = WatchTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 15.dp, bottom = 5.dp)


        )
        UserNameTextField(value = username, onValueChange = { username = it })

        // Password input field
        PassWordTextField(value = password, onValueChange = { password = it })

        Button(
            onClick = { onNavigate(username,password) },
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(.4f)
                .height(30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = WatchTheme.colorScheme.secondary
            )
        ) {
            Text(
                "Login",
                style = TextStyle(
                    fontFamily = Jura,
                    fontSize = 15.sp,
                    color = WatchTheme.colorScheme.primary
                ))
        }
    }

}


@Composable
fun UserNameTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Username") },
        maxLines = 1,
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = WatchTheme.colorScheme.onBackground,
            focusedContainerColor = WatchTheme.colorScheme.onBackground,
            unfocusedIndicatorColor = WatchTheme.colorScheme.onBackground,
            focusedIndicatorColor = WatchTheme.colorScheme.primary
        ),
        textStyle = TextStyle(
            fontFamily = Jura,
            fontSize = 15.sp,
            color = WatchTheme.colorScheme.primary
        )
    )
}

@Composable
fun PassWordTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        maxLines = 1,
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = WatchTheme.colorScheme.onBackground,
            focusedContainerColor = WatchTheme.colorScheme.onBackground,
            unfocusedIndicatorColor = WatchTheme.colorScheme.onBackground,
            focusedIndicatorColor = WatchTheme.colorScheme.primary
        ),
        textStyle = TextStyle(
            fontFamily = Jura,
            fontSize = 15.sp,
            color = WatchTheme.colorScheme.primary
        )
    )
}

