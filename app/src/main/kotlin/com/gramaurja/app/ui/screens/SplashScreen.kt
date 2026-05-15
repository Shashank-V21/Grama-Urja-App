package com.gramaurja.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gramaurja.app.R
import com.gramaurja.app.util.SeedData
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNext: () -> Unit) {
    LaunchedEffect(Unit) {
        try {
            SeedData.seedAll()
        } catch (e: Exception) {
            // Log or ignore if already seeded
        }
        delay(1500)
        onNext()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.tagline),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}
