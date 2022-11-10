package org.superside.constants.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.superside.constants.extension.UnleashExtension
import org.superside.constants.generator.KotlinFeatureGenerator
import org.superside.constants.service.FeatureFetcher
import java.io.File
import javax.inject.Inject

@CacheableTask
abstract class FeaturesCodegenTask @Inject constructor() : DefaultTask() {

    private val featureFetcher = FeatureFetcher()
    private val featureGenerator = KotlinFeatureGenerator()

    @TaskAction
    fun run() {
        val unleashExtension = project.extensions.getByType(UnleashExtension::class.java)
        assert(unleashExtension.url.isNotBlank()) { "Unleash URL must be set" }

        val generatedSrcDir = File(project.projectDir, "src/main/kotlin/")
        generatedSrcDir.mkdirs()
        try {
            featureGenerator.generate(featureFetcher.fetchFeatures(unleashExtension), unleashExtension, generatedSrcDir)
        } catch (e: Exception) {
            System.err.println("Unable to generate features from unleash:")
            e.printStackTrace()
        }
    }
}
