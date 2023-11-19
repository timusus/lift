plugins {
    id("lift.library")
    id("lift.android.hilt")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(project(":shared:common"))
            implementation(project(":shared:logging"))
        }
    }
}
