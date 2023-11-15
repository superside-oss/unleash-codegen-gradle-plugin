package com.superside.unleash.codegen.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.superside.unleash.codegen.model.Feature
import com.superside.unleash.codegen.model.FeaturesResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class DefaultUnleashFeatureClientFetcher : UnleashFeatureFetcher {
    private val objectMapper =
        ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val httpClient =
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(DEFAULT_TIMEOUT)
            .build()

    /**
     * Fetches the features from Unleash, using the java.net for networking.
     */
    override fun fetchFeatures(
        url: String,
        token: String,
        projects: List<String?>,
    ): List<Feature> {
        var uri = URI("${url}$CLIENT_API_PATH")
        projects.forEach {
            uri =
                if (projects[0] == it) {
                    URI("$url$CLIENT_API_PATH?project[]=$it")
                } else {
                    URI("$uri&project[]=$it")
                }
        }

        val request =
            HttpRequest.newBuilder().uri(uri).GET()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .timeout(DEFAULT_TIMEOUT)
                .build()

        val response =
            httpClient
                .send(request, HttpResponse.BodyHandlers.ofString())

        return objectMapper.readValue(response.body(), FeaturesResponse::class.java).features
            .stream()
            .sorted(Comparator.comparing { it.name.lowercase() })
            .toList()
    }

    private companion object {
        const val CLIENT_API_PATH = "client/features"
        val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(5)
    }
}
