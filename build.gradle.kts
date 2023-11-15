plugins {
    kotlin("jvm") version "1.9.0"

    `kotlin-dsl`
    `java-gradle-plugin`

    id("com.gradle.plugin-publish") version "1.2.1"
    id("org.jmailen.kotlinter") version "4.1.0"
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

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.8.1")
}

tasks.check {
    dependsOn("installKotlinterPrePushHook")
}

group = "org.superside"

gradlePlugin {
    website.set("https://github.com/superside-oss/unleash-codegen-gradle-plugin")
    vcsUrl.set("https://github.com/superside-oss/unleash-codegen-gradle-plugin")
    plugins {
        create("unleashCodegen") {
            id = "org.superside.unleash.codegen"
            implementationClass = "org.superside.unleash.codegen.UnleashPlugin"
            displayName = "Unleash codegen gradle plugin"
            description = "Gradle plugin for Unleash Feature Toggles code generation"
            version = project.version as String
            tags = listOf("unleash", "codegen", "feature toggle")
        }
    }
}
