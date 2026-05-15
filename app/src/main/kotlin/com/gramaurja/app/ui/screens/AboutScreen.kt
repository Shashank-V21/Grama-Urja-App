package com.gramaurja.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    val labelExtraSmall = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("About Grama-Urja", style = MaterialTheme.typography.titleLarge)
                        Text("MISSION & VERSION INFO", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF2E7D32)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Power, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Grama-Urja App", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Powering Rural Progress", style = MaterialTheme.typography.titleMedium, color = Color(0xFF2E7D32))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Surface(
                color = Color(0xFFF1F8E9),
                shape = CircleShape
            ) {
                Text(
                    text = "v2.0.1 (Production)",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF1B5E20)
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AboutInfoRow("Description", "Grama-Urja provides real-time power monitoring and irrigation management specifically designed for rural communities in Karnataka.")
                AboutInfoRow("Developers", "Grama-Urja Community Dev Team")
                AboutInfoRow("Data Engine", "Firebase Real-time Cloud Store")
                AboutInfoRow("Architecture", "MVVM + Jetpack Compose Native")
            }
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Text("© 2024 Grama-Urja Project", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text("Built for Community Impact", style = labelExtraSmall, color = Color.LightGray)
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun AboutInfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium, color = Color.Black.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = Color(0xFFF0F0F0))
    }
}
