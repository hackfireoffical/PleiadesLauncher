package net.kdt.pojavlaunch.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** Consistent corner radii used across cards, buttons, sheets, docks. */
object PleiadesRadii {
    val Xs = 6.dp
    val Sm = 10.dp
    val Md = 16.dp
    val Lg = 22.dp
    val Xl = 28.dp
    val Pill = 50.dp
}

val PleiadesShapes = Shapes(
    extraSmall = RoundedCornerShape(PleiadesRadii.Xs),
    small = RoundedCornerShape(PleiadesRadii.Sm),
    medium = RoundedCornerShape(PleiadesRadii.Md),
    large = RoundedCornerShape(PleiadesRadii.Lg),
    extraLarge = RoundedCornerShape(PleiadesRadii.Xl)
)
