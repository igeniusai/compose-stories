package ai.igenius.composestories

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Story(
    val path: String = "",
)