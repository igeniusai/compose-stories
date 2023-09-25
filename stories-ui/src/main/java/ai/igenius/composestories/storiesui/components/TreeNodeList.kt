package ai.igenius.composestories.storiesui.components

import ai.igenius.composestories.storiesui.models.FolderNode
import ai.igenius.composestories.storiesui.models.StoryNode
import ai.igenius.composestories.storiesui.models.TreeNode
import ai.igenius.composestories.storiesui.models.TreeNodeListValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun TreeNodeList(
    state: TreeNodeListState = rememberTreeNodeListState(),
    nightModeToggleState: NightModeToggleState? = null,
    title: String?,
    root: FolderNode,
    closeDrawer: () -> Unit
) {
    val nodeItems = root.children.map { state.current.value.getNodeItemStates(it) }.flatten()
    ModalDrawerSheet {
        Row(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                .padding(vertical = 4.dp)
        ) {
            title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
            }
            nightModeToggleState?.let {
                NightModeToggle(it)
            }
        }
        LazyColumn {
            items(nodeItems, key = { it.node.completePath }) {
                NodeItem(
                    model = it,
                    onClick = {
                        if(it.node is StoryNode) closeDrawer()
                        state.current.value = state.current.value.toggleNode(it.node)
                    }
                )
            }
        }
    }
}

@Composable
internal fun rememberTreeNodeListState(
    initialValue: TreeNodeListValue = TreeNodeListValue(null, emptyList())
): TreeNodeListState {
    return rememberSaveable(saver = TreeNodeListState.Saver()) {
        TreeNodeListState(initialValue)
    }
}

internal class TreeNodeListState(
    initialValue: TreeNodeListValue
) {
    val current = mutableStateOf(initialValue)

    companion object {
        fun Saver() =
            Saver<TreeNodeListState, TreeNodeListValue>(
                save = { it.current.value },
                restore = { TreeNodeListState(it) }
            )
    }
}

private fun TreeNodeListValue.getNodeItemStates(tree: TreeNode, rootDistance: Int = 0): List<NodeItemModel> =
    when (tree) {
        is StoryNode -> NodeItemModel(
                node = tree,
                rootDistance = rootDistance,
                selected = tree.id == selectedNodeId,
                isOpen = false
            ).let(::listOf)
        is FolderNode -> buildList {
            val isOpen = openedFolders.contains(tree.completePath)
            NodeItemModel(
                node = tree,
                rootDistance = rootDistance,
                selected = false,
                isOpen = isOpen,
            ).let(::add)

            if(isOpen) {
                tree.children.map {
                    getNodeItemStates(tree = it, rootDistance = rootDistance + 1)
                }.flatten().let(::addAll)
            }
        }
        else -> emptyList()
    }

internal fun TreeNodeListValue.toggleNode(node: TreeNode): TreeNodeListValue {
    return when (node) {
        is FolderNode -> copy(
            openedFolders = if(openedFolders.contains(node.completePath))
                    openedFolders.filter { it != node.completePath }
                else openedFolders + node.completePath
        )
        is StoryNode -> copy(selectedNodeId = node.id)
        else -> copy()
    }
}
