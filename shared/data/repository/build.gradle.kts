plugins {
    id("lift.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(project(":shared:logging"))
            implementation(project(":shared:data:database"))
            implementation(project(":shared:data:network"))
            implementation(project(":shared:domain"))
        }
    }
}
