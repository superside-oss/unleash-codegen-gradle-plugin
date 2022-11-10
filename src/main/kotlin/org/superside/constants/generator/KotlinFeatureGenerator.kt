package org.superside.constants.generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.superside.constants.extension.UnleashExtension
import org.superside.constants.model.Feature
import org.superside.constants.util.toEnumStyle
import org.superside.constants.util.toFormattedLine
import org.superside.constants.util.toMapOfWords
import java.io.File
import java.nio.file.Path

class KotlinFeatureGenerator : FeatureGenerator() {

    /**
     * Generates a Kotlin file with a class containing all the features from Unleash.
     *
     * @param features the list of features from Unleash.
     * @param unleashExtension the extension containing the configuration for the codegen.
     * @param generatedSrcDir the directory where the generated file will be placed.
     */
    override fun generate(
        features: List<Feature>,
        unleashExtension: UnleashExtension,
        generatedSrcDir: File
    ) {
        println(
            "Generating features to $generatedSrcDir/" +
                "${unleashExtension.packageName}/${unleashExtension.fileName}.kt"
        )

        val featureBuilder = TypeSpec.objectBuilder(unleashExtension.fileName)
            .addKdoc(FILE_HEADER_KDOC)
            .addAnnotation(
                AnnotationSpec.Companion.builder(Suppress::class)
                    .addMember(CodeBlock.of("names = [\"unused\", \"RedundantVisibilityModifier\"]"))
                    .build()
            )

        for (feature in features) {
            featureBuilder
                .addProperty(
                    PropertySpec.builder(feature.name.toEnumStyle(), String::class, KModifier.CONST)
                        .initializer("%S", feature.name)
                        .addKdoc(
                            generateValidDescription(feature) +
                                generateValidType(feature) +
                                "\n\n__Stale__: ${feature.stale}"
                        )
                        .build()
                )
        }

        FileSpec.builder(unleashExtension.packageName, unleashExtension.fileName)
            .addType(featureBuilder.build())
            .indent("\t")
            .build()
            .writeTo(Path.of("${generatedSrcDir.path}/"))
    }

    private fun generateValidType(feature: Feature): Any? {
        return if (feature.type.isNotEmpty()) {
            "\n\n__Type__: ${feature.type}"
        } else {
            "\n\n__Type__: empty"
        }
    }

    private fun generateValidDescription(feature: Feature): String {
        var description = "__Description__: "
        if (feature.description?.trim()?.isNotBlank() == true) {
            description += feature.description.toMapOfWords().toFormattedLine()
        } else {
            description += "empty"
        }

        return description
    }
}
