package io.github.ybarkouski.unleash.codegen.task

import io.github.ybarkouski.unleash.codegen.extension.UnleashExtension
import io.github.ybarkouski.unleash.codegen.generator.KotlinFeatureGenerator
import io.github.ybarkouski.unleash.codegen.service.DefaultUnleashFeatureClientFetcher
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

@CacheableTask
abstract class FeaturesCodegenTask @Inject constructor() : DefaultTask() {

    private val unleashFeatureClientFetcher = DefaultUnleashFeatureClientFetcher()
    private val featureGenerator = KotlinFeatureGenerator()

    @TaskAction
    fun run() {
        val unleashExtension = project.extensions.getByType(UnleashExtension::class.java)
        require(unleashExtension.url?.isNotBlank() == true) { "Unleash URL must be set" }
        require(unleashExtension.token?.isNotBlank() == true) { "Unleash token must be set" }

        try {
            featureGenerator.generate(
                unleashFeatureClientFetcher.fetchFeatures(
                    unleashExtension.url!!,
                    unleashExtension.token!!,
                    unleashExtension.projects
                ),
                unleashExtension,
                project.projectDir
            )
        } catch (e: Exception) {
            System.err.println("Unable to generate features from Unleash:")
            e.printStackTrace()
        }
    }

    override fun getDescription(): String {
        return "Generates feature constants from Unleash"
    }
}
