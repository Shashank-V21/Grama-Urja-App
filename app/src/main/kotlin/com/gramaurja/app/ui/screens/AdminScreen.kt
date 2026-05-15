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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.ui.viewmodel.DashboardViewModel
import com.gramaurja.app.data.repository.PowerRepository
import com.gramaurja.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(viewModel: DashboardViewModel) {
    val scope = rememberCoroutineScope()
    val powerRepo = PowerRepository()
    val authRepo = AuthRepository()
    val profile by viewModel.userProfile.collectAsState()
    val powerStatus by viewModel.powerStatus.collectAsState()
    
    val isPowerOn = powerStatus?.status == "ON"
    var isSimulatingFault by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Admin Console", style = MaterialTheme.typography.titleLarge)
                        Text("SYSTEM SIMULATION & MANAGEMENT", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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

            Text("HARDWARE SIMULATION", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            AdminSectionCard(
                title = "Power Grid Control",
                description = "Toggle transformer power for ${profile?.zoneId ?: "current zone"}.",
                icon = Icons.Default.FlashOn
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Grid Online Status")
                    Switch(
                        checked = isPowerOn, 
                        onCheckedChange = { isOn ->
                            scope.launch {
                                val status = if (isOn) "ON" else "OFF"
                                powerRepo.updatePowerStatus(
                                    profile?.zoneId ?: "z1",
                                    status,
                                    profile?.fullName ?: "Admin",
                                    authRepo.getCurrentUser()?.uid ?: ""
                                )
                            }
                        }
                    )
                }
            }

            AdminSectionCard(
                title = "Failure Scenarios",
                description = "Trigger mock system failures to test user notification paths.",
                icon = Icons.Default.Warning
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Simulate Fuse Failure")
                        Switch(checked = isSimulatingFault, onCheckedChange = { isSimulatingFault = it })
                    }
                    Button(
                        onClick = { /* Launch Notification */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB74D))
                    ) {
                        Text("PUSH REPAIR ALERT", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Text("DATA MANAGEMENT", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            AdminSectionCard(
                title = "Log Maintenance",
                description = "Truncate or clear activity logs for the current season.",
                icon = Icons.Default.DeleteSweep
            ) {
                OutlinedButton(
                    onClick = { /* Clear Logs */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFEBEE))
                ) {
                    Text("CLEAR ACTIVITY HISTORY")
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun AdminSectionCard(title: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E4E0))
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    }
                }
                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(description, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
            Divider(color = Color(0xFFF0F0F0))
            content()
        }
    }
}
