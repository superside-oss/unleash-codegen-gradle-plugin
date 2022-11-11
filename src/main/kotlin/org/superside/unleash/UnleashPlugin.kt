package org.superside.unleash

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.superside.unleash.extension.UnleashExtension
import org.superside.unleash.task.FeaturesCodegenTask

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
