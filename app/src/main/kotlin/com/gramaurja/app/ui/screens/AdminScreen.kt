package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Admin Panel") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Simulation Mode", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}) { Text("Toggle Power Status (Simulate)") }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}) { Text("Send Sample Notification") }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}) { Text("Clear All Logs (Simulate)") }
        }
    }
}
