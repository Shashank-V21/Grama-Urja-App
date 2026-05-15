package com.gramaurja.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.data.model.AppNotification
import com.gramaurja.app.data.model.StatusHistory
import com.gramaurja.app.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(viewModel: DashboardViewModel) {
    val history by viewModel.powerHistory.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Activity History", style = MaterialTheme.typography.titleLarge)
                        Text("RECENT EVENTS & NOTIFICATIONS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item { Text("POWER LOGS", style = MaterialTheme.typography.labelSmall, color = Color.Gray) }
            
            if (history.isEmpty()) {
                item {
                    Text("No power activity recorded yet.", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
                }
            } else {
                items(history) { log ->
                    HistoryListItem(log)
                }
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun NotificationListItem(notification: AppNotification) {
    val bgColor = if (notification.read) Color.White else Color(0xFFE8F5E9).copy(alpha = 0.3f)
    val iconColor = when(notification.kind) {
        "on" -> Color(0xFF2E7D32)
        "off" -> Color(0xFFD32F2F)
        else -> Color(0xFF1976D2)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (notification.kind == "on") Icons.Default.FlashOn else Icons.Default.FlashOff,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.message, style = MaterialTheme.typography.bodyMedium, fontWeight = if (notification.read) FontWeight.Normal else FontWeight.Bold)
                Text("2 hours ago", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            
            if (!notification.read) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.size(8.dp)
                ) {}
            }
        }
    }
}

@Composable
fun HistoryListItem(history: StatusHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                history.status,
                style = MaterialTheme.typography.labelSmall,
                color = if (history.status == "ON") Color(0xFF2E7D32) else Color(0xFFD32F2F),
                modifier = androidx.compose.ui.Modifier
                    .background(
                        (if (history.status == "ON") Color(0xFFE8F5E9) else Color(0xFFFFEBEE)),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
            Column {
                Text("Status changed to ${history.status}", style = MaterialTheme.typography.bodySmall)
                Text("Updated by ${history.updatedBy}", style = MaterialTheme.typography.labelSmall, color = Color.LightGray)
            }
        }
    }
}
