package org.superside.unleash.generator

import org.superside.unleash.extension.UnleashExtension
import org.superside.unleash.model.Feature
import java.io.File

abstract class FeatureGenerator {

    abstract fun generate(
        features: List<Feature>,
        unleashExtension: UnleashExtension,
        projectDirectory: File
    )

    companion object {
        const val FILE_HEADER_KDOC =
            "CLASS IS GENERATED BY THE UNLEASH CODEGEN GRADLE PLUGIN. DO NOT EDIT."
    }
}
