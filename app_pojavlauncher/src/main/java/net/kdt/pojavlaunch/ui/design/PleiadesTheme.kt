package net.kdt.pojavlaunch.ui.design

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Spacing scale. Use these instead of raw dp values. */
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
}

// Original palette: deep night-sky surfaces with star-blue / violet / mint accents.
private val DarkColors = darkColorScheme(
    primary = Color(0xFF7CC4FF),
    onPrimary = Color(0xFF00263F),
    primaryContainer = Color(0xFF0B3A5E),
    onPrimaryContainer = Color(0xFFCDE8FF),
    secondary = Color(0xFFB59CFF),
    onSecondary = Color(0xFF2A0E66),
    tertiary = Color(0xFF8DF0D0),
    onTertiary = Color(0xFF00382A),
    background = Color(0xFF0A0D1A),
    onBackground = Color(0xFFE3E6F5),
    surface = Color(0xFF121729),
    onSurface = Color(0xFFE3E6F5),
    surfaceVariant = Color(0xFF1B2140),
    onSurfaceVariant = Color(0xFFB4BAD6),
    outline = Color(0xFF454C73),
    error = Color(0xFFFF8A80),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF0F6CB8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCDE8FF),
    onPrimaryContainer = Color(0xFF001D33),
    secondary = Color(0xFF6B4FD8),
    tertiary = Color(0xFF00796B),
    background = Color(0xFFF6F7FD),
    onBackground = Color(0xFF12152A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF12152A),
    surfaceVariant = Color(0xFFE4E7F7),
    onSurfaceVariant = Color(0xFF454C73),
    outline = Color(0xFF7A81A6),
    error = Color(0xFFB3261E),
)

private val PleiadesShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

/**
 * Root theme. Dark-first. Custom typography is not designed yet; this uses the
 * Material 3 default scale for now.
 */
@Composable
fun PleiadesTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val reduceMotion = rememberReduceMotion()
    CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            shapes = PleiadesShapes,
            typography = Typography(),
            content = content,
        )
    }
}
