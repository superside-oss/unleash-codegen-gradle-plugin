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

    /**
     * Fetches the features from Unleash, using the java.net for networking.
     *
     * @param extension The extension containing the Unleash URL, Token and optionally projects.
     */
    fun fetchFeatures(extension: UnleashExtension): List<Feature> {
        var uri = URI("${extension.url}$CLIENT_API_PATH")
        if (extension.token.isBlank()) {
            throw IllegalArgumentException("Unleash token must be set")
        }
        extension.projects.forEach {
            if (extension.projects[0] == it) {
                uri = URI("${extension.url}$CLIENT_API_PATH?project[]=$it")
            } else {
                uri = URI("$uri&project[]=$it")
            }
        }

        val request = HttpRequest.newBuilder().uri(uri).GET()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", extension.token)
            .timeout(DEFAULT_TIMEOUT)
            .build()

        val response = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(DEFAULT_TIMEOUT)
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())

        return objectMapper.readValue(response.body(), FeaturesResponse::class.java).features
            .stream()
            .sorted(Comparator.comparing { it.name.toLowerCase() })
            .toList()
    }

    companion object {
        private const val CLIENT_API_PATH = "client/features"
        private val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(5)
    }
}
