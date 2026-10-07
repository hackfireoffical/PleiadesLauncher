package net.kdt.pojavlaunch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import net.kdt.pojavlaunch.ui.motion.LocalReduceMotion
import net.kdt.pojavlaunch.ui.motion.rememberReduceMotion

private val DarkScheme = darkColorScheme(
    primary = PleiadesColors.Nebula,
    onPrimary = Color.White,
    primaryContainer = PleiadesColors.NebulaDim,
    onPrimaryContainer = PleiadesColors.Starlight,
    secondary = PleiadesColors.Aurora,
    onSecondary = Color(0xFF00382A),
    secondaryContainer = Color(0xFF005C45),
    onSecondaryContainer = PleiadesColors.Aurora,
    tertiary = PleiadesColors.Ember,
    onTertiary = Color.White,
    error = PleiadesColors.Ember,
    onError = Color.White,
    background = PleiadesColors.Void,
    onBackground = PleiadesColors.OnDark,
    surface = PleiadesColors.DeepSpace,
    onSurface = PleiadesColors.OnDark,
    surfaceVariant = PleiadesColors.Panel,
    onSurfaceVariant = PleiadesColors.OnDarkMuted,
    outline = PleiadesColors.Outline,
    outlineVariant = PleiadesColors.Outline,
    surfaceContainer = PleiadesColors.Panel,
    surfaceContainerHigh = PleiadesColors.PanelElevated,
    surfaceContainerHighest = PleiadesColors.PanelElevated
)

private val LightScheme = lightColorScheme(
    primary = PleiadesColors.Nebula,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE4DEFF),
    onPrimaryContainer = PleiadesColors.NebulaDim,
    secondary = Color(0xFF006B52),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF8FF5D0),
    onSecondaryContainer = Color(0xFF002117),
    tertiary = PleiadesColors.Ember,
    onTertiary = Color.White,
    error = PleiadesColors.Ember,
    onError = Color.White,
    background = PleiadesColors.Cloud,
    onBackground = PleiadesColors.OnLight,
    surface = PleiadesColors.Mist,
    onSurface = PleiadesColors.OnLight,
    surfaceVariant = PleiadesColors.PanelLight,
    onSurfaceVariant = PleiadesColors.OnLightMuted,
    outline = PleiadesColors.OutlineLight,
    outlineVariant = PleiadesColors.OutlineLight,
    surfaceContainer = PleiadesColors.PanelLight,
    surfaceContainerHigh = Color(0xFFE8E4F4),
    surfaceContainerHighest = Color(0xFFE0DBF0)
)

/**
 * Root theme for all Compose UI.
 * Dark is the default; light is available when the system (or a future
 * in-app toggle) requests it. Does not touch the existing Views theme.
 */
@Composable
fun PleiadesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val reduceMotion = rememberReduceMotion()
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        typography = PleiadesTypography,
        shapes = PleiadesShapes
    ) {
        CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
            content()
        }
    }
}
