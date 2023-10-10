plugins {
    id("lift.library")
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)

                implementation(project(":shared:logging"))
            }
        }

        val androidMain by getting {
            dependencies {
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
    }
}
