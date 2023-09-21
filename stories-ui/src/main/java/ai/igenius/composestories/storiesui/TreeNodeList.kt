package ai.igenius.composestories.storiesui

import ai.igenius.composestories.storiesui.models.FolderNode
import ai.igenius.composestories.storiesui.models.StoryNode
import ai.igenius.composestories.storiesui.models.TreeNode
import android.os.Parcelable
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize

@Composable
fun TreeNodeList(
    state: TreeNodeListState = rememberTreeNodeListState(),
    title: String?,
    root: FolderNode,
) {
    val nodeItems = root.children.map { state.current.value.getNodeItemStates(it) }.flatten()
    ModalDrawerSheet {
        title?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                    .padding(vertical = 4.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(nodeItems, key = { it.node.completePath }) {
                NodeItem(
                    model = it,
                    onClick = {
                        state.current.value = state.current.value.toggleNode(it.node)
                    }
                )
            }
        }
    }
}

@Composable
fun rememberTreeNodeListState(
    initialValue: TreeNodeListValue = TreeNodeListValue(null, emptyList())
): TreeNodeListState {
    return rememberSaveable(saver = TreeNodeListState.Saver()) {
        TreeNodeListState(initialValue)
    }
}

class TreeNodeListState(
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

@Parcelize
data class TreeNodeListValue(
    val selectedNodeId: Int?,
    val openedFolders: List<String>,
) : Parcelable

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

fun TreeNodeListValue.toggleNode(node: TreeNode): TreeNodeListValue {
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
