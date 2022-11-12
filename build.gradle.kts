plugins {
    kotlin("jvm") version "1.6.21"

    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish-conventions`

    id("com.gradle.plugin-publish") version "1.0.0"
    id("org.jmailen.kotlinter") version "3.10.0"
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jboss.forge.roaster:roaster-jdt:2.26.0.Final")
    implementation("com.squareup:kotlinpoet:1.12.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
}

tasks.check {
    dependsOn("installKotlinterPrePushHook")
}

group = "org.superside"

gradlePlugin {
    plugins {
        create("unleashCodegen") {
            id = "org.superside.unleash"
            implementationClass = "org.superside.unleash.UnleashPlugin"
            displayName = "Unleash codegen gradle plugin"
            description = "Gradle plugin for Unleash Feature Toggles code generation"
            version = project.version as String
        }
    }
}

pluginBundle {
    website = "https://github.com/yahorbarkouski/unleash-gradle-plugin"
    vcsUrl = "https://github.com/yahorbarkouski/unleash-gradle-plugin.git"
    tags = listOf("unleash")
    pluginTags = mapOf(
        "unleashCodegen" to listOf("unleash")
    )
}
