package ai.igenius.composestories.example

import ai.igenius.composestories.Story
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ai.igenius.composestories.example.ui.theme.ComposeStoriesTheme
import ai.igenius.composestories.storiesui.StoriesScreen
import ai.igenius.composestories.storiesui.models.ComposeNode
import ai.igenius.composestories.storiesui.models.StoriesProvider
import ai.igenius.composestories.storiesui.models.StoryNode
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStoriesTheme {
                StoriesScreen("Stories Example app", appStoriesProvider)
            }
        }
    }
}

@Story("Ciao/come/stai")
@Preview
@Composable
fun ExampleComponentStory() {
    Text("Ciao bello")
}