package ai.igenius.composestories.storiesui.components

import ai.igenius.composestories.storiesui.models.FolderNode
import ai.igenius.composestories.storiesui.models.TreeNode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun NodeItem(
    model: NodeItemModel,
    onClick: () -> Unit,
) {
    val angle by animateFloatAsState(if (model.isOpen) 90f else 0f, label = "icon_rotation")
    NavigationDrawerItem(
        icon = model.icon?.let { {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.rotate(angle)
            )
        } },
        label = {
            Text(model.node.title)
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
    val isOpen: Boolean
) {
    val icon: ImageVector? get() =
        if(node is FolderNode) Icons.AutoMirrored.Filled.KeyboardArrowRight else null
}