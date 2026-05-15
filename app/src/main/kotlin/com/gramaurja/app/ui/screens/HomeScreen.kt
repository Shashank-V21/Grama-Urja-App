package com.gramaurja.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gramaurja.app.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: DashboardViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val powerStatus by viewModel.powerStatus.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Grama-Urja", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
                        Text("COMMUNITY MONITOR", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                },
                actions = {
                    IconButton(onClick = {}) { 
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(androidx.compose.material.icons.Icons.Default.Notifications, null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(Modifier.padding(24.dp)) {
                        Text("Current Location", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                        Text(profile?.villageId ?: "Angondhalli Village", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                        Text(profile?.zoneId ?: "Transformer Zone 1", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.9f))
                    }
                }
            }

            item {
                val statusOn = powerStatus?.status == "ON"
                val statusColor = if (statusOn) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                val accentColor = if (statusOn) Color(0xFF2E7D32) else Color(0xFFE65100)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E4E0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text("POWER STATUS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text(
                                    text = powerStatus?.status ?: "ON",
                                    style = MaterialTheme.typography.displayMedium,
                                    color = accentColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Last updated 4m ago",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.LightGray
                                )
                            }
                            
                            Surface(
                                shape = androidx.compose.foundation.shape.CircleShape,
                                color = statusColor,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Surface(
                                        shape = androidx.compose.foundation.shape.CircleShape,
                                        color = accentColor,
                                        modifier = Modifier.size(24.dp)
                                    ) {}
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        LinearProgressIndicator(
                            progress = 1f,
                            modifier = Modifier.fillMaxWidth().height(4.dp),
                            color = accentColor,
                            trackColor = statusColor,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("Capacity", "100 KVA", Modifier.weight(1f))
                    StatCard("Pumps", "42 Active", Modifier.weight(1f))
                }
            }

            item {
                Text("RECENT ACTIVITY", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            
            items(3) {
                ActivityItem()
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF5F5F5))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun ActivityItem() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(shape = androidx.compose.foundation.shape.CircleShape, color = Color(0xFFF9A825), modifier = Modifier.size(8.dp)) {}
            Column(modifier = Modifier.weight(1f)) {
                Text("Power Off scheduled for Zone 2", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                Text("12:30 PM", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}
