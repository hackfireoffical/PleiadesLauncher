package net.kdt.pojavlaunch.ui.motion

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalAccessibilityManager

/**
 * Central motion tokens for the entire app.
 * Every animation / transition should read from here so reduce-motion and
 * future tuning stay consistent.
 */
object MotionSpec {

    // --- Durations (ms) ---
    const val Instant = 0
    const val Fast = 120
    const val Normal = 220
    const val Slow = 360
    const val Emphasized = 480

    // --- Springs ---
    val SpringSnappy: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
    val SpringSoft: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    )
    val SpringStiff: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )

    // --- Tweens ---
    fun <T> tweenFast(): TweenSpec<T> = tween(durationMillis = Fast)
    fun <T> tweenNormal(): TweenSpec<T> = tween(durationMillis = Normal)
    fun <T> tweenSlow(): TweenSpec<T> = tween(durationMillis = Slow)
    fun <T> tweenEmphasized(): TweenSpec<T> = tween(durationMillis = Emphasized)

    /** Stagger delay between list items (ms). */
    const val StaggerItem = 40

    /** Max items that receive staggered entrance before the rest appear together. */
    const val StaggerMaxItems = 12
}

/**
 * CompositionLocal that reports whether the user prefers reduced motion.
 * Screens should skip non-essential animations when this is true.
 */
val LocalReduceMotion = staticCompositionLocalOf { false }

@Composable
@ReadOnlyComposable
fun rememberReduceMotion(): Boolean {
    val accessibilityManager = LocalAccessibilityManager.current
    // AccessibilityManager does not expose a direct "reduce motion" flag on all
    // API levels; we default to false and let a future Settings toggle override.
    // When a preference is added, read it here.
    return accessibilityManager?.isEnabled == true && false
}
