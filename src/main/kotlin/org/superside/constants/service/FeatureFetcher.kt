package org.superside.constants.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.superside.constants.extension.UnleashExtension
import org.superside.constants.model.Feature
import org.superside.constants.model.FeaturesResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class FeatureFetcher {

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun fetchFeatures(unleashExtension: UnleashExtension): Collection<Feature> {
        var uri = URI("${unleashExtension.url}client/features")
        if (unleashExtension.token.isBlank()) {
            throw IllegalArgumentException("Unleash token must be set")
        }
        unleashExtension.projects.forEach {
            if (unleashExtension.projects[0] == it) {
                uri = URI("${unleashExtension.url}client/features?project[]=$it")
            } else {
                uri = URI("$uri&project[]=$it")
            }
        }

        val request = HttpRequest.newBuilder().uri(uri).GET()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", unleashExtension.token)
            .timeout(Duration.ofSeconds(5))
            .build()

        val response = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(Duration.ofSeconds(5))
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())

        return objectMapper.readValue(response.body(), FeaturesResponse::class.java).features
            .stream()
            .sorted(Comparator.comparing { it.name.toLowerCase() })
            .toList()
    }
}
