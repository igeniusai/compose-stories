package ai.igenius.composestories.storiesui

import ai.igenius.composestories.storiesui.components.DynamicNavigationDrawer
import ai.igenius.composestories.storiesui.components.NightModeToggleState
import ai.igenius.composestories.storiesui.components.TreeNodeList
import ai.igenius.composestories.storiesui.components.rememberTreeNodeListState
import ai.igenius.composestories.storiesui.models.StoriesProvider
import ai.igenius.composestories.storiesui.models.generateFolderTree
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun StoriesScreen(
    title: @Composable RowScope.() -> Unit,
    storiesProvider: StoriesProvider,
    nightModeToggleState: NightModeToggleState? = null,
) {
    val context = LocalContext.current
    val viewModel: StoriesScreenViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return StoriesScreenViewModel(context) as T
            }
        }
    )
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    when(settings) {
        is SettingsState.Loading -> Unit
        is SettingsState.Data -> {
            val lastNodeId = (settings as SettingsState.Data).lastNodeId
            val treeState = rememberTreeNodeListState(
                nodeId = lastNodeId,
                onValueChange = { viewModel.saveLastStoryId(it.selectedNodeId) }
            )
            val drawerState = rememberDrawerState(if (lastNodeId != null) DrawerValue.Closed
            else DrawerValue.Open)
            val tree by remember { derivedStateOf { generateFolderTree(storiesProvider.stories) } }
            val selectedStory = storiesProvider.stories
                .find { it.id == treeState.current.value.selectedNodeId }
            val scope = rememberCoroutineScope()

            Surface {
                DynamicNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            TreeNodeList(
                                state = treeState,
                                nightModeToggleState = nightModeToggleState,
                                title = title,
                                root = tree
                            ) {
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
    }
}