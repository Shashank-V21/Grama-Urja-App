package com.gramaurja.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val GreenRural = Color(0xFF2E7D32)
val GreenDarkBrand = Color(0xFF1B5E20)
val BackgroundPale = Color(0xFFF4F7F4)
val BorderGray = Color(0xFFE0E4E0)
val DarkAccent = Color(0xFF1B1D1B)
val LightGreenSurface = Color(0xFFE8F5E9)

private val DarkColorScheme = darkColorScheme(
    primary = GreenLight,
    secondary = GreenRural,
    tertiary = OrangePower
)

private val LightColorScheme = lightColorScheme(
    primary = GreenRural,
    secondary = GreenDarkBrand,
    tertiary = OrangePower,
    background = BackgroundPale,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkAccent,
    onSurface = DarkAccent,
    outline = BorderGray
)

@Composable
fun GramaUrjaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
