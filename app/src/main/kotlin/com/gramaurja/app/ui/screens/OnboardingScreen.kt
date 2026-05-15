package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    var step by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(32.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { i ->
                    val color = if (i + 1 <= step) MaterialTheme.colorScheme.primary else Color.LightGray
                    Surface(modifier = Modifier.weight(1f).height(4.dp), color = color, shape = RoundedCornerShape(2.dp)) {}
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(text = "STEP $step OF 3", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            when (step) {
                1 -> {
                    Text(text = "Welcome to Grama-Urja", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Let's personalize your experience.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    Spacer(modifier = Modifier.height(48.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
                2 -> {
                    Text(text = "Select your Village", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Which area's power would you like to monitor?", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    Spacer(modifier = Modifier.height(48.dp))
                    OutlinedTextField(
                        value = village,
                        onValueChange = { village = it },
                        label = { Text("Search Village") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
                3 -> {
                    Text(text = "Select Transformer", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Pick the specific zone for your farm irrigation.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    Spacer(modifier = Modifier.height(48.dp))
                    OutlinedTextField(
                        value = zone,
                        onValueChange = { zone = it },
                        label = { Text("Zone Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (step < 3) step++ else onComplete()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(if (step < 3) "CONTINUE" else "START MONITORING", fontWeight = FontWeight.Bold)
            }
        }
    }
}
