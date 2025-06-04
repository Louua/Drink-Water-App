# 💧 Drink Water Reminder App

A simple Android app built with **Jetpack Compose**, designed to help users track their daily water intake.

---

## 🎯 Features

- Track daily water consumption
- Add water in increments (e.g., +250ml)
- View progress with a **progress bar**
- Show motivational message when reaching **2000 ml**
- Auto-hide success message after 3 seconds
- Reset button to restart tracking for the day

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **State Management**: `mutableStateOf`, `remember`
- **Data Persistence**: `SharedPreferences`
- **Animations**: `animateFloatAsState`
- **Material Design**: Material 3
- **Coroutines**: For auto-hiding messages

---

## 🧭 App Flow

1. **RegistrationActivity** – Saves user credentials
2. **LoginActivity** – Validates login
3. **MainActivity** – Main tracker screen with:
   - Progress bar
   - Add water button
   - Reset button
   - Motivational message at goal (2000ml)

---

## 📁 Project Structure
