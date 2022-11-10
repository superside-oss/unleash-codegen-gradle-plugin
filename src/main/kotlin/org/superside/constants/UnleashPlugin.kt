package org.superside.constants

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.superside.constants.extension.UnleashExtension
import org.superside.constants.task.FeaturesCodegenTask

@Suppress("unused")
class UnleashPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        project.extensions.create(
            "unleash",
            UnleashExtension::class.java
        )

        tasks.register("generateFeatures", FeaturesCodegenTask::class.java)
    }
}
