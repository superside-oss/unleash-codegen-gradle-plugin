pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "Superplugins"
            url = uri("https://gitlab.com/api/v4/projects/40186689/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                if (System.getenv("CI_JOB_TOKEN") != null) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")!!
                } else {
                    name = "Private-Token"
                    value = System.getenv("GITLAB_PRIVATE_TOKEN")
                }
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }
        }
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            name = "Spring Releases"
            url = uri("https://repo.spring.io/libs-release/")
        }
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}
