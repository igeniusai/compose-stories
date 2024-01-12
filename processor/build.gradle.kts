import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id("kotlin-kapt")
    alias(libs.plugins.shadow)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(libs.kotlin.poet)
    implementation(libs.auto.service)
    kapt(libs.auto.service)
}

tasks.withType<ShadowJar> {
    archiveFileName.set("processor.jar")
}