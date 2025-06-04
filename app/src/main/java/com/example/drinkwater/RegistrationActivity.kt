package com.example.drinkwater.ui.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight // ✅ Import added
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // ✅ Import added
import androidx.compose.ui.platform.LocalContext // ✅ Import added
import androidx.core.content.edit
import androidx.compose.material3.MaterialTheme // Optional but good to import

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrinkWaterReminderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RegistrationPage(onRegisterSuccess = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
fun RegistrationPage(onRegisterSuccess: () -> Unit) {
    val context = LocalContext.current // ✅ Used for SharedPreferences
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            fontSize = 32.sp, // ✅ Now works
            fontWeight = FontWeight.Bold // ✅ Now works
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                val sharedPref = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
                sharedPref.edit {
                    putString("email", email)
                    putString("password", password)
                }
                onRegisterSuccess()
            }
        }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onRegisterSuccess() }) {
            Text(text = "Already have an account? Login")
        }
    }
}