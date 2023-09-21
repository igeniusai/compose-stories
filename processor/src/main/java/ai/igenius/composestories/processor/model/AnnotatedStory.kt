package ai.igenius.composestories.processor.model

import ai.igenius.composestories.Story
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

data class AnnotatedStory(
    val annotation: Story,
    val packageName: String,
    val sourceSimpleName: String
) {

    constructor(
        processingEnv: ProcessingEnvironment,
        element: Element
    ) : this(
        annotation = element.getAnnotation(Story::class.java),
        packageName = processingEnv.elementUtils.getPackageOf(element).qualifiedName.toString(),
        sourceSimpleName = element.simpleName.toString(),
    )
}