package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.R

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onBackToLogin: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(32.dp)) {
            Text(text = "Join Grama-Urja", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = "Create your account to start monitoring.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Mobile Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

            Spacer(modifier = Modifier.height(48.dp))

            Button(onClick = onRegisterSuccess, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(28.dp)) {
                Text("CREATE ACCOUNT", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
                Text("Already have an account? Sign In", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
