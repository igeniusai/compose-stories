package ai.igenius.composestories.processor.specs

import ai.igenius.composestories.processor.model.AnnotatedStory
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import java.io.File

class StoriesProviderSpec(
    storiesByPackage: List<Pair<String, ComposeStorySpec>>
) {
    private val packageName = getRootPackage(storiesByPackage.map { it.first })

    private val typeSpec = TypeSpec
        .objectBuilder(STORIES_PROVIDER_OBJECT_NAME)
        .addSuperinterface(STORIES_PROVIDER_INTERFACE)
        .addTypes(storiesByPackage.map { it.second.build() })
        .addProperty(
            PropertySpec
                .builder(
                    "stories",
                    List::class.asClassName().plusParameter(COMPOSE_NODE_MODEL)
                )
                .addModifiers(KModifier.OVERRIDE)
                .getter(
                    FunSpec
                        .getterBuilder()
                        .addStatement(
                            "return listOf(${Array(storiesByPackage.size) { "%L" }.joinToString(", ")})",
                            *storiesByPackage.map { it.second.name }.toTypedArray()
                        ).build()
                ).build()
        )

    private val fileSpec = FileSpec
        .builder(packageName, STORIES_PROVIDER_OBJECT_NAME)
        .addType(typeSpec.build())

    fun writeTo(file: File) = fileSpec.build().writeTo(file)

    companion object {
        private const val STORIES_PROVIDER_OBJECT_NAME = "appStoriesProvider"
    }
}

private fun getRootPackage(packages: List<String>): String {
    var result: String? = null
    packages.forEach { packageName ->
        result?.let {
            result = it.commonPrefixWith(packageName)
        } ?: run {
            result = packageName
        }
    }

    return checkNotNull(result) { "no root package name" }
}

private const val MODELS_PACKAGE = "ai.igenius.composestories.storiesui.models"
val STORIES_PROVIDER_INTERFACE = ClassName(MODELS_PACKAGE, "StoriesProvider")
val COMPOSE_NODE_MODEL = ClassName(MODELS_PACKAGE, "ComposeNode")
val COMPOSABLE_ANNOTATION = ClassName("androidx.compose.runtime", "Composable")