plugins {
    id("lift.library")
}
android {
    namespace = "com.simplecityapps.lift.logging"
}

kotlin {
    sourceSets {
        val androidMain by getting {
            dependencies {
                api(libs.logcat)
            }
        }
    }
}
