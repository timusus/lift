plugins {
    id("lift.library")
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.kotlinx.serialization.json)
                implementation(project(":shared:domain"))
                implementation(project(":shared:common"))
            }
        }
    }
}