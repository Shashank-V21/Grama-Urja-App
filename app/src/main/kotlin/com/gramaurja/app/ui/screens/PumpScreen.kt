package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import com.gramaurja.app.data.model.PumpLog
import com.gramaurja.app.ui.viewmodel.PumpViewModel
import com.gramaurja.app.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PumpScreen(pumpViewModel: PumpViewModel, dashboardViewModel: DashboardViewModel) {
    val profile by dashboardViewModel.userProfile.collectAsState()
    val pumpStatus by pumpViewModel.pumpStatus.collectAsState()
    val pumpLogs by pumpViewModel.pumpLogs.collectAsState()

    var crop by remember { mutableStateOf("Paddy") }
    var acres by remember { mutableStateOf("5") }
    
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
                    
                    if (pumpStatus?.status == "ON") {
                        Text(
                            "Started by ${pumpStatus?.updatedBy}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Control Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9).copy(alpha = 0.5f))
            ) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Irrigation Control", style = MaterialTheme.typography.titleLarge, color = Color(0xFF1B5E20))
                    
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { pumpViewModel.togglePump("ON", profile?.fullName ?: "User") },
                            modifier = Modifier.weight(1f).height(56.dp),
                            enabled = pumpStatus?.status != "ON",
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("START", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { pumpViewModel.togglePump("OFF", profile?.fullName ?: "User") },
                            modifier = Modifier.weight(1f).height(56.dp),
                            enabled = pumpStatus?.status == "ON",
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Icon(Icons.Default.Block, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("STOP", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            // Logs Section
            Text("YOUR RECENT SESSIONS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            
            if (pumpLogs.isEmpty()) {
                Text("No sessions recorded yet.", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
            } else {
                pumpLogs.forEach { log ->
                    PumpLogItem(log)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PumpLogItem(log: PumpLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F1F1))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (log.action == "ON") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (log.action == "ON") Icons.Default.PlayArrow else Icons.Default.Block,
                        contentDescription = null,
                        tint = if (log.action == "ON") Color(0xFF2E7D32) else Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Column {
                Text(
                    text = "Pump ${if (log.action == "ON") "Started" else "Stopped"}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = log.createdAt.toDate().toString().substring(0, 16),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}
