package ai.igenius.composestories.storiesui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun rememberWindowType(): WindowType {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowType.Compact
        configuration.screenWidthDp < 840 -> WindowType.Medium
        else -> WindowType.Expanded
    }
}

sealed class WindowType {
    object Compact : WindowType()
    object Medium : WindowType()
    object Expanded : WindowType()
}