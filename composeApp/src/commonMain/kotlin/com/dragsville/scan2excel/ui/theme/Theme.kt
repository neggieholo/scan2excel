package com.dragsville.scan2excel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    onPrimary = Color.White,
    surface = Color.White,
    background = Color.White
)

//private val DarkColorScheme = darkColorScheme(
//    primary = DarkBlue,
//    onPrimary = Color.Black,
//    surface = Color(0xFF121212),
//    background = Color(0xFF121212)
//)

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    // THE FLOOR: Deep Charcoal/Black
    background = Color(0xFF101010),
    // THE CARDS & TOP BAR: Slightly lighter gray so they "pop"
    surface = Color(0xFF1E1E1E),
    // TEXT ON THE SURFACE: Pure white or very light gray
    onSurface = Color(0xFFEEEEEE)
)

@Composable
fun ScanToExcelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // No more Build.VERSION checks!
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}