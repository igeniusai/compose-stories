package ai.igenius.composestories.storiesui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
internal fun rememberWindowType(): WindowType {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowType.Compact
        configuration.screenWidthDp < 840 -> WindowType.Medium
        else -> WindowType.Expanded
    }
}

internal sealed class WindowType {
    data object Compact : WindowType()
    data object Medium : WindowType()
    data object Expanded : WindowType()
}