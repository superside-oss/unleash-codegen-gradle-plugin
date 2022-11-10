package org.superside.constants.model

data class FeaturesResponse(
    val version: Long,
    val features: List<Feature> = emptyList(),
)
