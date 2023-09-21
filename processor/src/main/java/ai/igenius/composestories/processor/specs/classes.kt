package ai.igenius.composestories.processor.specs

import com.squareup.kotlinpoet.ClassName

private const val MODELS_PACKAGE = "ai.igenius.composestories.storiesui.models"
val STORIES_PROVIDER_INTERFACE = ClassName(MODELS_PACKAGE, "StoriesProvider")
val COMPOSE_NODE_MODEL = ClassName(MODELS_PACKAGE, "ComposeNode")
val COMPOSABLE_ANNOTATION = ClassName("androidx.compose.runtime", "Composable")