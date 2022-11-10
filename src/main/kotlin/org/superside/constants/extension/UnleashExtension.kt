package org.superside.constants.extension

import org.gradle.api.plugins.ExtensionAware

/**
 * Base extension for the Unleash codegen plugin.
 *
 * @see org.superside.constants.task.FeaturesCodegenTask
 */
abstract class UnleashExtension : ExtensionAware {

    var packageName = ""
    var fileName = "Features"
    var url = ""
    var token = ""
    var projects = emptyList<String>()
}
