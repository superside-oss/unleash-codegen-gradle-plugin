package org.superside.unleash.codegen.extension

/**
 * Base extension for the Unleash codegen plugin.
 *
 * @see org.superside.unleash.codegen.task.FeaturesCodegenTask
 **/
abstract class UnleashExtension {

    /**
     * The URL of the Unleash instance.
     */
    var url: String? = null

    /**
     * Unleash auth token, could be Client.
     */
    var token: String? = null

    /**
     * Package name for generated files.
     */
    var packageName: String = ""

    /**
     * Unleash projects to generate features for.
     */
    var projects: List<String> = emptyList()
}
