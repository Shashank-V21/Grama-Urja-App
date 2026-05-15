# Grama-Urja: Rural Solar Microgrid Management

Grama-Urja is a community-focused Android application built to empower rural communities with real-time monitoring of solar-powered microgrids and smart control of irrigation pumps. It facilitates efficient energy usage and provides transparency in power distribution.

## 🚀 Features

- **Real-time Grid Monitoring:** Instantly track Power ON/OFF status for specific transformer zones using Firestore real-time listeners.
- **Smart Pump Control:** Remote Start/Stop functionality for irrigation pumps, integrated with safety checks (power availability).
- **Session History:** Comprehensive logs of pumping activities, duration, and user actions.
- **Village-wise Partitioning:** Data is organized by Village and Transformer Zone to ensure users only see relevant information.
- **Multi-language Support:** Full localization for **English** and **Kannada**, making it accessible to rural farmers.
- **Admin Dashboard:** Exclusive panel for operators to manage grid status and oversee system health.

## 🛠 Tech Stack

- **Language:** [Kotlin 2.0.21](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3
- **Architecture:** MVVM (Model-View-ViewModel) with Repository pattern
- **Backend:** [Firebase](https://firebase.google.com/)
    - **Authentication:** Email/Password based login
    - **Cloud Firestore:** Real-time NoSQL database for states and logs
    - **Cloud Messaging (FCM):** For grid status alerts (configurable)
- **Dependency Management:** Kotlin DSL (Gradle 8.7+)
- **Image Loading:** Coil

## 📦 Project Structure

```text
com.gramaurja.app
├── data
│   ├── model       # Firestore Data Models (Village, Zone, PowerStatus, PumpLog)
│   └── repository  # Firebase Logic (Auth, Power, Pump Repositories)
├── ui
│   ├── screens     # Compose Screens (Home, Pump, Admin, Activity, etc.)
│   ├── theme       # Design System (Color, Type, Theme)
│   └── viewmodel   # Business Logic & State Management
└── util            # Helpers & Data Seeding
```

## ⚙️ Setup Instructions (Android Studio)

### 1. Prerequisites
- Android Studio Ladybird (or newer)
- JDK 21
- A Firebase Project

### 2. Firebase Configuration
1. Create a project in the [Firebase Console](https://console.firebase.google.com/).
2. Add an **Android App** with package name: `com.gramaurja.app`.
3. Download `google-services.json` and place it in the `app/` directory.
4. **Authentication:** Enable **Email/Password** provider.
5. **Firestore:** Enable Firestore in **Test Mode** (or update rules for Production).

### 3. Build & Run
1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle (it will automatically use the versions defined in `build.gradle.kts`).
4. Run on a physical device or emulator (API 24+ recommended).

## 🧪 Testing Real-time Features

The app includes a `SeedData` utility that populates sample villages and zones on the first run.
1. **Real-time Sync:** Log in on two different devices. Change the "Power Grid" status from the Admin section on one; observe the instant UI update on the second.
2. **Zone Logic:** User A in "Zone 1" won't receive power status updates meant for "Zone 2".

## 🛡 License
Distributed under the MIT License. See `LICENSE` for more information.
