package io.github.ybarkouski.unleash.codegen

import io.github.ybarkouski.unleash.codegen.extension.UnleashExtension
import io.github.ybarkouski.unleash.codegen.task.FeaturesCodegenTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle plugin for generating feature constants from Unleash.
 */
@Suppress("unused")
class UnleashPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        extensions.create("unleash", UnleashExtension::class.java)
        tasks.register("generateFeatures", FeaturesCodegenTask::class.java)
    }
}
