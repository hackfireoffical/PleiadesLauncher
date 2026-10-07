package net.kdt.pojavlaunch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay
import net.kdt.pojavlaunch.ui.design.LocalReduceMotion
import net.kdt.pojavlaunch.ui.design.MotionSpec
import net.kdt.pojavlaunch.ui.design.PleiadesTheme
import net.kdt.pojavlaunch.ui.design.Spacing
import net.kdt.pojavlaunch.ui.design.respectReduceMotion

/**
 * Debug-only. Start with:
 *   am start -n com.hackfire.pleiades.debug/net.kdt.pojavlaunch.ui.ComposeShowcaseActivity
 */
class ComposeShowcaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PleiadesTheme { Showcase() } }
    }
}

@Composable
private fun Showcase() {
    val reduce = LocalReduceMotion.current
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.statusBarsPadding().padding(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Text("Pleiades \u00B7 Compose OK", style = MaterialTheme.typography.headlineMedium)
            Text("Reduce motion: $reduce", color = MaterialTheme.colorScheme.onSurfaceVariant)
            PressCard()
            StaggerList()
        }
    }
}

@Composable
private fun PressCard() {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = MotionSpec.bouncy<Float>().respectReduceMotion(),
        label = "press",
    )
    var taps by remember { mutableIntStateOf(0) }
    Card(
        onClick = { taps++ },
        modifier = Modifier.fillMaxWidth().scale(scale),
        shape = MaterialTheme.shapes.large,
        interactionSource = source,
    ) {
        Text("Tap me \u2014 taps: $taps", Modifier.padding(Spacing.lg))
    }
}

@Composable
private fun StaggerList() {
    val labels = listOf("Versions", "Downloads", "Controls", "Logs")
    val reduce = LocalReduceMotion.current
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        labels.forEachIndexed { index, label ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                if (!reduce) delay(index * MotionSpec.Duration.Stagger.toLong())
                visible = true
            }
            val alpha by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = MotionSpec.standard<Float>().respectReduceMotion(),
                label = "stagger",
            )
            Surface(
                modifier = Modifier.fillMaxWidth().alpha(alpha),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Text(label, Modifier.padding(Spacing.md))
            }
        }
    }
}
