package com.superside.unleash.codegen.service

import com.superside.unleash.codegen.model.Feature

interface UnleashFeatureFetcher {
    /**
     * Fetches the features from the Unleash API.
     *
     * @param url the URL of the Unleash instance.
     * @param token the token to use for authentication.
     * @param projects the projects to fetch features for.
     *
     * @return the list of Unleash features.
     */
    fun fetchFeatures(
        url: String,
        token: String,
        projects: List<String?>,
    ): List<Feature>
}
