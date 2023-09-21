package ai.igenius.composestories.storiesui

import ai.igenius.composestories.storiesui.models.FolderNode
import ai.igenius.composestories.storiesui.models.TreeNode
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NodeItem(
    model: NodeItemModel,
    onClick: () -> Unit,
) {
    val icon = when (model.node) {
        is FolderNode -> {
            if(model.isOpen) Icons.Default.KeyboardArrowUp
            else Icons.Default.ArrowDropDown
        }
        else -> Icons.Default.Build
    }
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = null) },
        label = {
            Text(
                model.node.title,
            )
        },
        selected = model.selected,
        onClick = onClick,
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
            .padding(start = (24 * model.rootDistance).dp)
    )
}

@Stable
data class NodeItemModel(
    val node: TreeNode,
    val rootDistance: Int,
    val selected: Boolean,
    val isOpen: Boolean,
)