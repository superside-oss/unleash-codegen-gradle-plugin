package org.superside.constants.extension

import org.gradle.api.plugins.ExtensionAware

abstract class UnleashExtension : ExtensionAware {

    var packageName = "org.superside.constants"
    var fileName = "Features"
    var url = ""
    var token = "fakeToken"
    var ktlintEnabled = false
}
