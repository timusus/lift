pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "lift"
include(":androidApp")
include(":shared:domain")
include(":shared:database")
include(":shared:model")
include(":shared:data")
include(":shared:logging")
include(":shared:network")
include(":shared:domain")
include(":shared:framework")
include(":androidApp:maps")
