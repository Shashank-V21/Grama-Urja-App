package com.gramaurja.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gramaurja.app.R
import com.gramaurja.app.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.language_toggle),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.End)
        )
        
        Spacer(modifier = Modifier.height(48.dp))

        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ಗ್ರಾಮ-ಊರ್ಜಾ",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
        )
        Text(
            text = "COMMUNITY POWER MONITOR",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(R.string.sign_in_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.use_email_continue),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.login(email, password, onLoginSuccess) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = !loading,
            shape = RoundedCornerShape(28.dp)
        ) {
            if (loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            else Text(stringResource(R.string.sign_in_button), fontWeight = FontWeight.Bold)
        }

        error?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text(stringResource(R.string.sign_up_option))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.footer_monitor),
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}
