package ai.igenius.composestories

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Story(
    val title: String = "",
    val description: String = "",
    val variants: Array<String> = ["Default"]
)