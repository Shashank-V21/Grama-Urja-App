package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    var step by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = "Step $step of 3", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))

        when (step) {
            1 -> {
                Text(text = "Welcome! Let's get to know you.", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            2 -> {
                Text(text = "Select your village.", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                // Dummy selection...
                OutlinedTextField(
                    value = village,
                    onValueChange = { village = it },
                    label = { Text("Village Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            3 -> {
                Text(text = "Select your transformer zone.", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = zone,
                    onValueChange = { zone = it },
                    label = { Text("Zone Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (step < 3) step++ else onComplete()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (step < 3) "Next" else "Finish")
        }
    }
}
