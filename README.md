# Grama-Urja: Rural Power Monitoring App (Android)

Grama-Urja (ಗ್ರಾಮ-ಊರ್ಜಾ) is a native Android application built with Kotlin and Jetpack Compose for rural power monitoring and irrigation support in Karnataka.

## Features
- **Real-time Power Status**: Check if a transformer zone is ON/OFF/UNKNOWN.
- **Village & Zone Selection**: Tailored monitoring for your specific field location.
- **Pump Control (Admin/Operator)**: Remotely start or stop irrigation pumps.
- **Irrigation Estimator**: Calculate pump running time based on crop type and acres.
- **Notifications**: Receive alerts when power status changes.
- **Admin Panel**: Manage villages, zones, and users.
- **Multi-language Support**: English and Kannada.

## Technology Stack
- **Language**: Kotlin 2.2.10
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Repository Pattern
- **Reactive Programming**: Kotlin Coroutines & Flow
- **Backend**: Firebase (Authentication & Firestore)
- **Navigation**: Navigation Compose

## Project Structure
- `app/src/main/kotlin/com/gramaurja/app/data`: Models and Repositories.
- `app/src/main/kotlin/com/gramaurja/app/ui`: ViewModels and Compose Screens.
- `app/src/main/res`: XML resources including English and Kannada strings.
- `firestore.rules`: Secure rules for the database.

## Setup Instructions for Android Studio
1. **Export the Project**: Use the export tool in AI Studio to download the ZIP file.
2. **Open in Android Studio**: Extract and open the project.
3. **Firebase Setup**:
   - Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com).
   - Add an Android app with package name `com.gramaurja.app`.
   - Download `google-services.json` and place it in the `app/` directory.
   - Enable Email/Password authentication and Firestore.
4. **Build & Run**: Sync Gradle and run on a device or emulator.

## How to Test
- **Login/Register**: Use a test email and password.
- **Onboarding**: Complete the profile and select the sample village "Angondhalli".
- **Real-time Status**: Use the Admin Panel (in Simulation Mode or via Firestore console) to change the `powerStatus` for a zone.
- **Pump Control**: Navigate to the Pump tab and test the Start/Stop buttons (requires Admin role).

## Seed Data
The project includes a `SeedData` utility to populate your Firestore database with a sample village and zones for demo purposes.
