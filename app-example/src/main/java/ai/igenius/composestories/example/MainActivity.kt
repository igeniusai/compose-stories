package ai.igenius.composestories.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ai.igenius.composestories.example.ui.theme.ComposeStoriesTheme
import ai.igenius.composestories.storiesui.StoriesScreen
import ai.igenius.composestories.storiesui.models.ComposeNode
import ai.igenius.composestories.storiesui.models.StoryNode
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {

    private val mockStories = listOf(
        MockStoryNode(1, "Only title"),
        MockStoryNode(2, "Only title2"),
        MockStoryNode(3, "folder/Only title3"),
        MockStoryNode(4, "folder/Only title4"),
        MockStoryNode(5, "folder/subfolder/Only title4"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStoriesTheme {
                StoriesScreen("Stories Example app", mockStories)
            }
        }
    }
}

data class MockStoryNode(
    override val id: Int,
    override val completePath: String,
    override val description: String? = null,
    override val variants: List<String> = emptyList(),
): ComposeNode {
    @Composable
    override fun compose() {
        Text("$id Ciao bello")
    }
}