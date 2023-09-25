package ai.igenius.composestories.storiesui.models

/**
 * Interface that defines a node (folder o story)
 */
interface TreeNode {

    // Node complete path
    val completePath: String

    // Path divided by / that includes title
    val splitPath get() = completePath.split(DIVIDER).filterNot { it.isBlank() }

    // Node's title
    val title: String
        get() = splitPath.lastOrNull() ?: ""

    // Path divided by / that doesn't include title
    val path: List<String>
        get() = splitPath.toMutableList().also { it.removeLast() }

    // splitPath without blank spaces
    val cleanPath get() = splitPath.joinToString(DIVIDER)

    companion object {
        const val DIVIDER = "/"
    }
}

internal fun generateFolderTree(stories: List<StoryNode>) =
    FolderNode().also { root ->
        stories.forEach { generateCompletePath(root, it) }
    }

private fun generateCompletePath(root: FolderNode, story: StoryNode) {
    var lastFolder = root
    story.path.forEach {
        lastFolder = lastFolder.getOrGenerateSubfolder(it)
    }
    lastFolder.updateOrAdd(story)
}