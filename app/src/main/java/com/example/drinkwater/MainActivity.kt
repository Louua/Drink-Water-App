package com.example.drinkwater

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkWaterApp()
        }
    }
}

@Composable
fun DrinkWaterApp() {
    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("water_data", Context.MODE_PRIVATE) }

    var waterIntake by remember {
        mutableStateOf(sharedPref.getInt("intake", 0))
    }

    var dailyGoal by remember {
        mutableStateOf(sharedPref.getInt("goal", 2000))
    }

    val editor = sharedPref.edit()

    val animatedProgress by animateFloatAsState(
        targetValue = if (dailyGoal > 0) waterIntake.toFloat() / dailyGoal else 0f,
        label = "Progress Animation"
    )

    fun updateWater(amount: Int) {
        if (waterIntake + amount <= dailyGoal) {
            waterIntake += amount
        } else {
            waterIntake = dailyGoal
        }
        editor.putInt("intake", waterIntake).apply()
    }

    fun resetIntake() {
        waterIntake = 0
        editor.putInt("intake", 0).apply()
    }

    // 🎉 State for showing/hiding motivational message
    var showGoalReachedMessage by remember { mutableStateOf(false) }

    // Automatically hide message after 3 seconds
    if (showGoalReachedMessage) {
        LaunchedEffect(Unit) {
            delay(3000)
            showGoalReachedMessage = false
        }
    }

    // Check goal when waterIntake changes
    LaunchedEffect(waterIntake) {
        if (waterIntake >= dailyGoal) {
            showGoalReachedMessage = true
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFE3F2FD)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "💧 Drink Water Reminder",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Goal: $dailyGoal ml", fontSize = 18.sp)
                    Text(text = "Today: $waterIntake ml", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .width((animatedProgress * 360).dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { updateWater(250) },
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "+250 ml", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { resetIntake() },
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp)
                ) {
                    Text(text = "Reset", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Stay hydrated!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )
            }

            // 🎉 Show motivational message if goal reached
            if (showGoalReachedMessage) {
                MotivationalMessage()
            }
        }
    }
}

@Composable
fun MotivationalMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFD0F4DE),
            tonalElevation = 8.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "🎉 Great job!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You've reached your daily goal of 2000 ml!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}