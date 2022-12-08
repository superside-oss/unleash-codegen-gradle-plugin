package org.superside.unleash.codegen.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.superside.unleash.codegen.extension.UnleashExtension
import org.superside.unleash.codegen.generator.KotlinFeatureGenerator
import org.superside.unleash.codegen.service.DefaultUnleashFeatureClientFetcher
import org.superside.unleash.codegen.util.snakeToCamelCaseWithFirstLetterCapital
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

        val features = unleashFeatureClientFetcher.fetchFeatures(
            unleashExtension.url!!,
            unleashExtension.token!!,
            unleashExtension.projects
        )

        features.forEach { (unleashProject, features) ->
            try {
                featureGenerator.generate(
                    features,
                    unleashExtension.packageName,
                    unleashProject.snakeToCamelCaseWithFirstLetterCapital(),
                    project.projectDir
                )
            } catch (e: Exception) {
                System.err.println("Unable to generate features from Unleash for project $unleashProject: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    override fun getDescription(): String {
        return "Generates feature constants from Unleash"
    }
}
