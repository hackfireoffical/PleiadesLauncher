package net.kdt.pojavlaunch.ui.design

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * The single source of truth for animation timing. Every screen, dialog and component
 * must take its durations, curves and springs from here instead of hardcoding them.
 */
object MotionSpec {
    object Duration {
        const val Instant = 90
        const val Short = 160
        const val Medium = 280
        const val Long = 450
        /** Delay between consecutive items in a staggered list. */
        const val Stagger = 40
    }

    object Curves {
        val Standard = CubicBezierEasing(0.2f, 0f, 0f, 1f)
        val Emphasized = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
        val Exit = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
    }

    fun <T> standard(durationMs: Int = Duration.Medium): TweenSpec<T> =
        tween(durationMillis = durationMs, easing = Curves.Standard)

    fun <T> emphasized(durationMs: Int = Duration.Long): TweenSpec<T> =
        tween(durationMillis = durationMs, easing = Curves.Emphasized)

    fun <T> exit(durationMs: Int = Duration.Short): TweenSpec<T> =
        tween(durationMillis = durationMs, easing = Curves.Exit)

    /** Playful spring for press feedback and cards. */
    fun <T> bouncy(): SpringSpec<T> =
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)

    /** Settles without overshoot; for layout and position changes. */
    fun <T> smooth(): SpringSpec<T> =
        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow)
}

/** True when the user disabled system animations (animator duration scale = 0). */
val LocalReduceMotion = staticCompositionLocalOf { false }

/** Turns any spec into an instant jump when reduce-motion is on. */
@Composable
fun <T> AnimationSpec<T>.respectReduceMotion(): AnimationSpec<T> =
    if (LocalReduceMotion.current) snap() else this

private fun readAnimatorScale(context: Context): Float =
    Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)

/** Tracks the system animator scale and updates live when the user changes it. */
@Composable
fun rememberReduceMotion(): Boolean {
    val context = LocalContext.current
    var reduce by remember { mutableStateOf(readAnimatorScale(context) == 0f) }
    DisposableEffect(context) {
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                reduce = readAnimatorScale(context) == 0f
            }
        }
        context.contentResolver.registerContentObserver(
            Settings.Global.getUriFor(Settings.Global.ANIMATOR_DURATION_SCALE), false, observer
        )
        onDispose { context.contentResolver.unregisterContentObserver(observer) }
    }
    return reduce
}
