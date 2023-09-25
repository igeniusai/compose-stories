package ai.igenius.composestories.storiesui.models

import ai.igenius.composestories.storiesui.models.TreeNode.Companion.DIVIDER

/**
 * Class to define a gorup of stories
 * @param completePath is the path including the folder name,
 *          The defaul value is the root path
 */
internal class FolderNode(
    override val completePath: String = ROOT_FOLDER
) : TreeNode {

    private val _children: MutableList<TreeNode> = mutableListOf()

    // List of all children nodes ordered by CompareChildren
    val children get() = _children.toList().sortedWith(CompareChildren())

    /**
     * The complete folder path, including the folder name
     */
    override val cleanPath: String
        get() = if (isRoot) ROOT_FOLDER
        else super.cleanPath

    // is true if the folder doesn't have any parent (root)
    private val isRoot get() = completePath == ROOT_FOLDER

    /**
     * Update or add a new child node based on the given node's name
     * @param node to add or update
     */
    fun updateOrAdd(node: TreeNode): Boolean {
        val toUpdate = _children.find { it.completePath == node.completePath }
        return toUpdate?.let {
            val index = _children.indexOf(it)
            _children.removeAt(index)
            _children.add(index, node)
            true
        } ?: run {
            _children.add(node)
            false
        }
    }

    /**
     * Create or returns the fodler with the given name
     * @param name of the subfolder
     * @returns the subfolder that matches the given name or a new folder inserted as child
     */
    fun getOrGenerateSubfolder(name: String): FolderNode =
        (if (!isRoot) "$completePath$DIVIDER$name" else name)
            .let { completePath ->
                (_children.find { it.completePath == completePath } as? FolderNode)
                    ?: FolderNode(completePath).also {
                        _children.add(it)
                    }
            }

    /**
     * Returns the number of levels to the given folder
     * @param child to measure the distance
     * @returns distance of the given folder,
     *      -1 if not present inside the folders tree,
     *      0 if the node is the same
     */
    fun distance(child: TreeNode) =
        if (child == this) 0
        else if (!isParent(child)) -1
        else child.cleanPath.removePrefix("$cleanPath$DIVIDER").split(DIVIDER).size

    /**
     * @param child to check
     * @returns true if the given child is present in the folder's tree
     */
    fun isParent(child: TreeNode) = if (isRoot) true
    else child.cleanPath.startsWith("$cleanPath$DIVIDER")

    private companion object {
        // Path of root
        const val ROOT_FOLDER = ""
    }
}

/**
 * Class used to order a list of nodes givin priority to the type (folders first)
 * and in a second moment to the name
 */
private class CompareChildren : Comparator<TreeNode> {
    override fun compare(a: TreeNode, b: TreeNode): Int {
        val byType = compareType(a, b)
        return if (byType == 0) compareName(a, b) else byType
    }

    private fun compareType(a: TreeNode, b: TreeNode): Int = when {
        a::class == b::class -> 0
        a is FolderNode -> -1
        else -> 1
    }

    private fun compareName(a: TreeNode, b: TreeNode): Int =
        a.completePath.compareTo(b.completePath)
}