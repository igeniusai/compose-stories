package ai.igenius.composestories.storiesui.models

/**
 * Generic story type
 */
interface StoryNode : TreeNode {

    // unique story identifier
    val id: Int

    // story description
    val description: String?

    /**
     * List of variants, used to create the variants meno of a story,
     * If the defined story is [StoryFragment],
     * when the user will select one of these, onVariantSelected will be invoked
     */
    val variants: List<String>
}