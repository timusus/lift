plugins {
    id("lift.library")
    id("lift.android.hilt")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
    }
}

android {
    namespace = "com.simplecityapps.lift.common"
}
