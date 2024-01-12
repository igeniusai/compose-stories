package ai.igenius.composestories.example

import ai.igenius.composestories.Story
import ai.igenius.composestories.example.ui.theme.ComposeStoriesTheme
import ai.igenius.composestories.storiesui.StoriesScreen
import ai.igenius.composestories.storiesui.components.NightModeToggleState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(isDark) }

            ComposeStoriesTheme(isDarkTheme) {
                StoriesScreen(
                    title = {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                imageVector = Icons.Sharp.Settings,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                            Text(
                                text = "Stories Example app",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    },
                    storiesProvider = appStoriesProvider,
                    nightModeToggleState = NightModeToggleState(isDarkTheme) { isDarkTheme = it }
                )
            }
        }
    }
}

@Story("Buttons/Primary")
@Preview
@Composable
fun PrimaryButtonShowcase() {
    Button(onClick = {}) {
        Text("Primary Button")
    }
}

@Story("Buttons/Text")
@Preview
@Composable
fun TextButtonShowcase() {
    TextButton(onClick = {}) {
        Text("Text Button")
    }
}