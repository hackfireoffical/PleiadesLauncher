package net.kdt.pojavlaunch.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Original Pleiades palette — dark-first, glass-friendly.
 * Not derived from ZL2 or any existing launcher skin.
 */
object PleiadesColors {

    // Core brand
    val Nebula = Color(0xFF7C5CFF)          // primary accent
    val NebulaDim = Color(0xFF5A3FD4)
    val Aurora = Color(0xFF3DFFB5)          // secondary / success
    val Ember = Color(0xFFFF6B4A)           // error / warning
    val Starlight = Color(0xFFE8E6F2)       // high-emphasis text (dark)

    // Surfaces — dark
    val Void = Color(0xFF0B0A12)            // deepest background
    val DeepSpace = Color(0xFF14121F)       // scaffold
    val Panel = Color(0xFF1C1A2B)           // cards / sheets
    val PanelElevated = Color(0xFF262338)
    val Glass = Color(0x991C1A2B)           // frosted overlay (~60% alpha)
    val Outline = Color(0x33FFFFFF)

    // Surfaces — light
    val Cloud = Color(0xFFF4F2FA)
    val Mist = Color(0xFFFFFFFF)
    val PanelLight = Color(0xFFF0EDF8)
    val OutlineLight = Color(0x1A000000)

    // Text
    val OnDark = Color(0xFFE8E6F2)
    val OnDarkMuted = Color(0x99E8E6F2)
    val OnLight = Color(0xFF1A1826)
    val OnLightMuted = Color(0x991A1826)
}
