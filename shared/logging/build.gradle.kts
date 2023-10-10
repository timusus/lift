plugins {
    id("lift.library")
}
android {
    namespace = "com.simplecityapps.shuttle.logging"
}

kotlin {

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "logging"
//        }
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.logcat)
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
    }
}
