package com.gramaurja.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Activity : Screen("activity")
    object Pump : Screen("pump")
    object Settings : Screen("settings")
    object Admin : Screen("admin")
    object About : Screen("about")
}
