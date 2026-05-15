package com.gramaurja.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
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
    
    val authViewModel = AuthViewModel()
    val dashboardViewModel = DashboardViewModel()
    val pumpViewModel = PumpViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Activity.route,
        Screen.Pump.route,
        Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    val items = listOf(
                        Triple(Screen.Home, "Home", Icons.Default.Home),
                        Triple(Screen.Activity, "Activity", Icons.Default.History),
                        Triple(Screen.Pump, "Pump", Icons.Default.FlashOn),
                        Triple(Screen.Settings, "Settings", Icons.Default.Settings)
                    )
                    items.forEach { (screen, label, icon) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (auth.currentUser == null) Screen.Login.route else Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(onNext = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
                })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = { 
                        navController.navigate(Screen.Home.route) { 
                            popUpTo(Screen.Login.route) { inclusive = true } 
                        } 
                    },
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
            composable(Screen.Home.route) {
                HomeScreen(viewModel = dashboardViewModel)
            }
            composable(Screen.Activity.route) {
                ActivityScreen(viewModel = dashboardViewModel)
            }
            composable(Screen.Pump.route) {
                PumpScreen(pumpViewModel = pumpViewModel, dashboardViewModel = dashboardViewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = dashboardViewModel,
                    onLogout = {
                        auth.signOut()
                        navController.navigate(Screen.Login.route) { popUpTo(0) }
                    },
                    onNavigateToAdmin = { navController.navigate(Screen.Admin.route) },
                    onNavigateToAbout = { navController.navigate(Screen.About.route) }
                )
            }
            composable(Screen.Admin.route) { AdminScreen(viewModel = dashboardViewModel) }
            composable(Screen.About.route) { AboutScreen() }
        }
    }
}
