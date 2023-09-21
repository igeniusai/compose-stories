package ai.igenius.composestories.processor.specs

import ai.igenius.composestories.processor.model.AnnotatedStory
import com.squareup.kotlinpoet.*

class ComposeStorySpec(
    id: Int,
    story: AnnotatedStory,
) {

    val name = "story_$id"

    private val properties = mutableListOf(
        // id
        PropertySpec.builder("id", Int::class, KModifier.OVERRIDE)
            .initializer("%L", id),

        // completePath
        PropertySpec.builder("completePath", String::class, KModifier.OVERRIDE)
            .initializer("%S", story.annotation.path),
    )

    private val functions = listOf(
        // generateFragment
        FunSpec
            .builder("compose")
            .addModifiers(KModifier.OVERRIDE)
            .addAnnotation(COMPOSABLE_ANNOTATION)
            .addStatement("return %L()", "${story.packageName}.${story.sourceSimpleName}")
    )

    private val typeSpec = TypeSpec
        .objectBuilder(name)
        .addSuperinterface(COMPOSE_NODE_MODEL)
        .addProperties(properties.mapNotNull { it.build() })
        .addFunctions(functions.map { it.build() })

    fun build() = typeSpec.build()
}