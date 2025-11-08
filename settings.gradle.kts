pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Wow Call Recorder"
include(":app")
include(":core:designsystem")
include(":core:data")
include(":core:domain")
include(":core:telephony")
include(":core:recording")
include(":core:ml")
include(":feature:setup")
include(":feature:home")
include(":feature:detail")
include(":feature:export")
 