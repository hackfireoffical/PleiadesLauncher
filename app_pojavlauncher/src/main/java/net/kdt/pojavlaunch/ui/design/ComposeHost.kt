package net.kdt.pojavlaunch.ui.design

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

/**
 * Hosts Compose content inside an existing View-based Fragment, so screens can be
 * migrated one at a time. Return the result from onCreateView().
 */
fun Fragment.pleiadesComposeView(content: @Composable () -> Unit): ComposeView =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { PleiadesTheme { content() } }
    }
