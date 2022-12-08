package org.superside.unleash.codegen.service

import org.superside.unleash.codegen.model.Feature

interface UnleashFeatureFetcher {

    /**
     * Fetches the features from the Unleash API.
     *
     * @param url the URL of the Unleash instance.
     * @param token the token to use for authentication.
     * @param projects the projects to fetch features for.
     *
     * @return the map of Unleash features grouped by Project.
     */
    fun fetchFeatures(url: String, token: String, projects: List<String?>): MutableMap<String, List<Feature>>
}
