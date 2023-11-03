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
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
    }
}

rootProject.name = "lift"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":shared:auth")
include(":shared:common")
include(":shared:data:database")
include(":shared:data:network")
include(":shared:data:repository")
include(":shared:domain")
include(":shared:logging")

include(":android:app")
include(":android:sync:work")

include(":shared:framework")
