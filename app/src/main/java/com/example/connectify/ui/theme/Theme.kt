package com.example.connectify.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.connectify.utils.ThemeMode


private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkOnBackground,
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    tertiary = DarkTertiary,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    error = DarkError,
    onError = DarkOnError
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightOnBackground,
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    tertiary = LightTertiary,
    surface = LightSurface,
    onSurface = LightOnSurface,
    error = LightError,
    onError = LightOnError
)

@Composable
fun ConnectifyTheme(
    themeMode : ThemeMode,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val configuration = LocalConfiguration.current

    val isDark = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.FOLLOW_SYSTEM -> {
            (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                    Configuration.UI_MODE_NIGHT_YES
        }
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDark -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}