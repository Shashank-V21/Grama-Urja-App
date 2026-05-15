package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.ui.viewmodel.PumpViewModel
import com.gramaurja.app.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PumpScreen(pumpViewModel: PumpViewModel, dashboardViewModel: DashboardViewModel) {
    val profile by dashboardViewModel.userProfile.collectAsState()
    val pumpStatus by pumpViewModel.pumpStatus.collectAsState()

    var crop by remember { mutableStateOf("Paddy") }
    var acres by remember { mutableStateOf("5") }
    var pumpSize by remember { mutableStateOf("Medium Pump") }

    LaunchedEffect(profile?.zoneId) {
        profile?.zoneId?.let { pumpViewModel.setZone(it) }
    }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { 
                    Column {
                        Text("Irrigation Control", style = MaterialTheme.typography.titleLarge)
                        Text("MANAGE PUMP SESSIONS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (pumpStatus?.status == "ON") Color(0xFFE8F5E9) else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E4E0))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("CURRENT PUMP STATUS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = pumpStatus?.status ?: "OFF",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (pumpStatus?.status == "ON") Color(0xFF2E7D32) else Color.Black
                    )
                }
            }

            // Estimate Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9).copy(alpha = 0.5f))
            ) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Irrigation Estimator", style = MaterialTheme.typography.titleLarge, color = Color(0xFF1B5E20))
                            Text("CALCULATE WATER NEED", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32).copy(alpha = 0.6f))
                        }
                    }
                    
                    OutlinedTextField(
                        value = crop,
                        onValueChange = { crop = it },
                        label = { Text("Crop Type") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = acres,
                        onValueChange = { acres = it },
                        label = { Text("Acres") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA5D6A7))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(crop, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp)) {
                                    Text("$acres Acres", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                                }
                            }
                            val estimatedTime = (acres.toDoubleOrNull() ?: 0.0) * 52 
                            val hours = estimatedTime.toInt() / 60
                            val minutes = estimatedTime.toInt() % 60
                            Text(
                                String.format("%02d:%02d:00", hours, minutes),
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text("ESTIMATED RUNTIME", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }

                    // Control Section
                    if (profile?.role == "admin" || profile?.role == "operator") {
                        Button(
                            onClick = { pumpViewModel.togglePump("ON", profile?.fullName ?: "User") },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            enabled = pumpStatus?.status != "ON",
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text("START PUMP", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            if (profile?.role != "admin" && profile?.role != "operator") {
                Text("Viewer mode: Only admins can control the pump.", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
