package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = Grenat,
    onPrimary = Blanc,
    primaryContainer = GrenatProfond,
    onPrimaryContainer = Blanc,
    secondary = Olive,
    onSecondary = Blanc,
    secondaryContainer = CremeFonce,
    onSecondaryContainer = GrenatProfond,
    tertiary = Moutarde,
    onTertiary = Encre,
    tertiaryContainer = MoutardeClaire,
    onTertiaryContainer = GrenatProfond,
    background = CremeClair,
    onBackground = Encre,
    surface = Blanc,
    onSurface = Encre,
    surfaceVariant = Creme,
    onSurfaceVariant = EncreMoyenne,
    outline = BordureLegere,
    outlineVariant = Color(0xFFEFE6D5)
)

private val DarkColorScheme = darkColorScheme(
    primary = Grenat,
    onPrimary = Blanc,
    primaryContainer = GrenatProfond,
    onPrimaryContainer = Blanc,
    secondary = Olive,
    onSecondary = Blanc,
    secondaryContainer = Grenat,
    onSecondaryContainer = Creme,
    tertiary = Moutarde,
    onTertiary = Encre,
    background = Color(0xFF1B1416),
    onBackground = Creme,
    surface = Color(0xFF281E21),
    onSurface = Creme,
    surfaceVariant = Color(0xFF3B2A2F),
    onSurfaceVariant = CremeFonce,
    outline = Color(0xFF5A444C)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
