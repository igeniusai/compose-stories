package ai.igenius.composestories.processor

import ai.igenius.composestories.Story
import ai.igenius.composestories.processor.model.AnnotatedStory
import ai.igenius.composestories.processor.specs.ComposeStorySpec
import ai.igenius.composestories.processor.specs.StoriesProviderSpec
import com.google.auto.service.AutoService
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes("ai.igenius.composestories.Story")
@SupportedOptions(StoryProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor::class)
class StoryProcessor : AbstractProcessor() {

    override fun process(set: Set<TypeElement?>?, roundEnvironment: RoundEnvironment?): Boolean {

        val generatedSourcesRoot = File(processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty())

        val elements = roundEnvironment?.getElementsAnnotatedWith(Story::class.java)

        val stories = elements?.map {AnnotatedStory(processingEnv, it) }

        if (roundEnvironment?.processingOver() != true)
            stories?.let { generateProvider(generatedSourcesRoot, it) }

        return false
    }

    private fun generateProvider(
        generatedSourcesRoot: File,
        stories: List<AnnotatedStory>
    ) = StoriesProviderSpec(
        stories.map {
            it.packageName to ComposeStorySpec(stories.indexOf(it), it)
        }
    ).writeTo(generatedSourcesRoot)

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}