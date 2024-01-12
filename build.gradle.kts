// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.android.library) apply false
    id("maven-publish")
}
true // Needed to make the Suppress annotation work for the plugins block

val githubProperties = java.util.Properties()
githubProperties.load(java.io.FileInputStream(rootProject.file("github.properties"))) //Set env variable GPR_USER & GPR_API_KEY if not adding a properties file

val libraryVersion = "0.1.4"
val githubGroupId = "ai.igenius.composestories"

val coreId = "core"
val storiesUiId = "stories-ui"
val processorId = "processor"

publishing {
    publications {
        register(coreId, MavenPublication::class.java) {
            groupId = githubGroupId
            artifactId = coreId
            version =  libraryVersion
            artifact("./$coreId/build/libs/${coreId}.jar")
        }
        register(processorId, MavenPublication::class.java) {
            groupId = githubGroupId
            artifactId = processorId
            version = libraryVersion
            artifact("./$processorId/build/libs/${processorId}.jar")
        }
        register(storiesUiId, MavenPublication::class.java) {
            groupId = githubGroupId
            artifactId = storiesUiId
            version = libraryVersion
            artifact("./$storiesUiId/build/outputs/aar/$storiesUiId-release.aar")
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/igeniusai/compose-stories")
            credentials {
                username = githubProperties["gpr.usr"]?.toString() ?: System.getenv("GPR_USER") ?: ""
                password = githubProperties["gpr.key"]?.toString() ?: System.getenv("GPR_API_KEY") ?: ""
            }
        }
    }
}