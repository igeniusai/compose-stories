package ai.igenius.composestories.storiesui.components

import ai.igenius.composestories.storiesui.resources.icons.rememberDarkModeImage
import ai.igenius.composestories.storiesui.resources.icons.rememberLightModeImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun NightModeToggle(
    state: NightModeToggleState
) {
    val icon = if(state.value)
        rememberDarkModeImage()
    else rememberLightModeImage()

    Switch(
        checked = state.value,
        onCheckedChange = { state.onUpdate(!state.value) },
        colors = SwitchDefaults.colors(
            uncheckedBorderColor = MaterialTheme.colorScheme.surface,
            uncheckedIconColor = MaterialTheme.colorScheme.onSurface,
            uncheckedThumbColor = Color.Transparent,
            uncheckedTrackColor = MaterialTheme.colorScheme.surface,
            checkedBorderColor = MaterialTheme.colorScheme.surface,
            checkedIconColor = MaterialTheme.colorScheme.onSurface,
            checkedThumbColor = Color.Transparent,
            checkedTrackColor = MaterialTheme.colorScheme.surface
        ),
        thumbContent = {
            Icon(imageVector = icon, contentDescription = null)
        }
    )
}

class NightModeToggleState(
    val value: Boolean,
    val onUpdate: (newValue: Boolean) -> Unit
)