package ai.igenius.composestories.storiesui.models

import androidx.compose.runtime.Composable

interface ComposeNode: StoryNode {

    @Composable
    fun compose()
}