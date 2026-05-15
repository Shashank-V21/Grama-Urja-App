package com.gramaurja.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gramaurja.app.navigation.Screen
import com.gramaurja.app.ui.theme.GramaUrjaTheme
import com.gramaurja.app.ui.screens.*
import com.gramaurja.app.ui.viewmodel.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            GramaUrjaTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    
    // Simple state-based VMs for demo purposes in this single file or use Hilt/factory in real app
    val authViewModel = AuthViewModel()
    val dashboardViewModel = DashboardViewModel()
    val pumpViewModel = PumpViewModel()

    val startDestination = if (auth.currentUser == null) Screen.Login.route else Screen.Splash.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNext = {
                navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { navController.navigate(Screen.Splash.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Onboarding.route) },
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onComplete = {
                navController.navigate(Screen.Home.route) { popUpTo(Screen.Onboarding.route) { inclusive = true } }
            })
        }
            HomeScreen(viewModel = dashboardViewModel)
        }
        composable(Screen.Pump.route) {
            PumpScreen(pumpViewModel = pumpViewModel, dashboardViewModel = dashboardViewModel)
        }
        composable(Screen.Admin.route) { AdminScreen() }
        composable(Screen.About.route) { AboutScreen() }
    }
}
