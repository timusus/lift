plugins {
    id("lift.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(project(":shared:model"))
                implementation(project(":shared:database"))
                implementation(project(":shared:network"))
                implementation(project(":shared:logging"))
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
