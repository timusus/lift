plugins {
    id("lift.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(project(":shared:common"))
        }
    }
}
