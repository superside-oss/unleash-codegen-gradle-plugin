package org.superside.unleash.codegen.extension

/**
 * Base extension for the Unleash codegen plugin.
 *
 * @see org.superside.unleash.codegen.task.FeaturesCodegenTask
 **/
abstract class UnleashExtension {
    var url: String? = null
    var token: String? = null
    var packageName: String = ""
    var fileName: String = "Features"
    var projects: List<String> = emptyList()
}
