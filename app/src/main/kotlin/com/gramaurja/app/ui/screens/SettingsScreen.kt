package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: DashboardViewModel, onLogout: () -> Unit, onNavigateToAdmin: () -> Unit, onNavigateToAbout: () -> Unit) {
    val profile by viewModel.userProfile.collectAsState()
    var notificationsEnabled by remember { mutableStateOf(profile?.notificationsEnabled ?: true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("App Settings", style = MaterialTheme.typography.titleLarge)
                        Text("PROFILE & PREFERENCES", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E4E0))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        shape = androidx.compose.foundation.shape.CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = profile?.fullName?.take(1)?.uppercase() ?: "U",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                    }
                    Column {
                        Text(profile?.fullName ?: "User Name", style = MaterialTheme.typography.titleLarge)
                        Text(profile?.email ?: "user@example.com", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Text(profile?.role?.uppercase() ?: "VIEWER", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Text("PREFERENCES", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            SettingsItem(
                icon = Icons.Default.Language,
                title = "Preferred Language",
                subtitle = if (profile?.language == "kn") "ಕನ್ನಡ" else "English",
                onClick = {}
            )

            SettingsToggle(
                icon = Icons.Default.Notifications,
                title = "Push Notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            Text("PROJECT", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            SettingsItem(
                icon = Icons.Default.Info,
                title = "About Grama-Urja",
                onClick = onNavigateToAbout
            )

            if (profile?.role == "admin") {
                SettingsItem(
                    icon = Icons.Default.AdminPanelSettings,
                    title = "Admin Management Panel",
                    tint = MaterialTheme.colorScheme.primary,
                    onClick = onNavigateToAdmin
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Icon(Icons.Default.Logout, null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text("Sign Out", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String? = null, tint: Color = Color.Black, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, null, tint = tint.copy(alpha = 0.6f), modifier = Modifier.size(24.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                subtitle?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray) }
            }
            Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
        }
    }
}

@Composable
fun SettingsToggle(icon: ImageVector, title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, null, tint = Color.Black.copy(alpha = 0.6f), modifier = Modifier.size(24.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}
