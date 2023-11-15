package org.superside.unleash.codegen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.superside.unleash.codegen.extension.UnleashExtension
import org.superside.unleash.codegen.task.FeaturesCodegenTask

/**
 * Gradle plugin for generating feature git-tracking constants from Unleash.
 */
@Suppress("unused")
class UnleashPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit =
        with(project) {
            extensions.create("unleash", UnleashExtension::class.java)
            tasks.register("generateFeatures", FeaturesCodegenTask::class.java)
        }
}
