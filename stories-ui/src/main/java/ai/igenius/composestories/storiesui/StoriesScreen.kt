package ai.igenius.composestories.storiesui

import ai.igenius.composestories.storiesui.models.StoriesProvider
import ai.igenius.composestories.storiesui.models.generateFolderTree
import ai.igenius.composestories.storiesui.utils.WindowType
import ai.igenius.composestories.storiesui.utils.rememberWindowType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun StoriesScreen(
    title: String? = null,
    provider: StoriesProvider
) {
    val treeState = rememberTreeNodeListState()
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val tree by remember { derivedStateOf { generateFolderTree(provider.stories) } }
    val selectedStory = provider.stories.find { it.id == treeState.current.value.selectedNodeId }
    val scope = rememberCoroutineScope()

    Surface {
        DynamicNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    TreeNodeList(treeState, title, tree) {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    selectedStory?.compose()
                }
            }
        )
    }
}

@Composable
fun DynamicNavigationDrawer(
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

