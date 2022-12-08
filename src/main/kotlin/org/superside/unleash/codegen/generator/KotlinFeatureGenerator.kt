package org.superside.unleash.codegen.generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.superside.unleash.codegen.model.Feature
import org.superside.unleash.codegen.util.toEnumStyle
import org.superside.unleash.codegen.util.toFormattedLine
import org.superside.unleash.codegen.util.toMapOfWords
import java.io.File
import java.nio.file.Path

class KotlinFeatureGenerator : FeatureGenerator() {

    /**
     * Generates a Kotlin file with a class containing all the features from Unleash.
     *
     * @param features the list of features from Unleash.
     * @param unleashExtension the extension containing the configuration for the codegen.
     * @param projectDirectory the project directory where the file will be generated.
     */
    override fun generate(
        features: List<Feature>,
        packageName: String,
        projectName: String,
        projectDirectory: File
    ) {
        val sourceSetDirectory = File(projectDirectory, KOTLIN_SOURCE_SET)
        println(
            "Generating features to $sourceSetDirectory/" +
                "$packageName/${projectName}Features.kt"
        )

        val featureBuilder = TypeSpec.objectBuilder("${projectName}Features")
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

        sourceSetDirectory.mkdirs()
        FileSpec.builder(packageName, "${projectName}Features")
            .addType(featureBuilder.build())
            .indent("\t")
            .build()
            .writeTo(Path.of("${sourceSetDirectory.path}/"))
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

    companion object {
        private const val KOTLIN_SOURCE_SET = "src/main/kotlin/"
    }
}
