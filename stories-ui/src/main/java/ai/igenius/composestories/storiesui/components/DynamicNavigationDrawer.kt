package ai.igenius.composestories.storiesui.components

import ai.igenius.composestories.storiesui.utils.WindowType
import ai.igenius.composestories.storiesui.utils.rememberWindowType
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable

@Composable
internal fun DynamicNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    when(rememberWindowType()) {
        is WindowType.Expanded -> PermanentNavigationDrawer(
            drawerContent = drawerContent,
            content = content
        )
        else -> ModalNavigationDrawer(
            drawerContent = drawerContent,
            drawerState = drawerState,
            content = content
        )
    }
}