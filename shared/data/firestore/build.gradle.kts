plugins {
    id("lift.library")
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:common"))
                implementation(project(":shared:data:database"))
                implementation(project(":shared:domain"))
                implementation(project(":shared:logging"))
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val androidMain by getting {
            dependencies {
                api(platform(libs.firebase.bom.get()))
                api(libs.firebase.firestore)
            }
        }
    }
}