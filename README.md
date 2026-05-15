# Grama-Urja: Rural Solar Microgrid Management

Grama-Urja is a community-driven Android application designed to manage and monitor solar-powered irrigation pumps and microgrid status in rural villages.

## Features
- **Real-time Grid Monitoring:** Get instant updates on Power ON/OFF status for your specific transformer zone.
- **Smart Pump Control:** Start and stop irrigation pumps based on power availability.
- **Usage History:** Track your pumping sessions and energy usage.
- **Admin Panel:** Manage village zones, users, and transformer status (Admin only).
- **Multilingual:** Supports English and Kannada for rural accessibility.

## Firebase Setup Instructions
To get this app running on your device, follow these steps:

1. **Create Firebase Project:** Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project named "Grama-Urja".
2. **Add Android App:**
   - Register the app with package name: `com.gramaurja.app`
   - Download the `google-services.json` file.
   - Place this file inside the `app/` folder of this project.
3. **Enable Authentication:**
   - Go to the Authentication section.
   - Enable the **Email/Password** sign-in provider.
4. **Enable Cloud Firestore:**
   - Create a database in **Production** or **Test** mode.
   - Deploy the security rules (see `firestore.rules` if provided or use default allowed for development).
5. **Real-time Setup:**
   - The app will automatically seed sample data (Villages, Zones) on the first login.

## How to Test Real-time Updates
1. **Using Two Devices/Tabs:**
   - Log in as a User on one device (or the AI Studio preview).
   - Log in as an Admin on another device.
   - As an Admin, toggle the "Power Grid" status in the Admin Panel.
   - Observe the "Power Status" card on the User's Home screen updating instantly without refresh.
2. **Zone Partitioning:**
   - Create two users in different Transformer Zones.
   - Toggle power for Zone A; only the user in Zone A should see the status change.

## Tech Stack
- **Kotlin:** 1.9.23
- **Jetpack Compose:** Material 3
- **Firebase:** Auth, Firestore, Messaging (BOM 32.8.0)
- **Coroutines:** Flow for real-time data streams
