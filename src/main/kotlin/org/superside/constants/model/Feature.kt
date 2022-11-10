package org.superside.constants.model

data class Feature(
    val name: String,
    val description: String?,
    val type: String,
    val enabled: Boolean,
    val stale: Boolean,
    val strategies: List<Strategy> = emptyList(),
    val tags: List<Tag> = emptyList(),
)

data class Strategy(
    val name: String,
    val parameters: Map<String, String> = emptyMap(),
)

data class Tag(
    val id: Long,
    val type: String,
    val value: String,
)
