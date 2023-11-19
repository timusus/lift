plugins {
    id("lift.library")
    id("lift.android.hilt")
}
android {
    namespace = "com.simplecityapps.lift.auth"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:common"))
                implementation(project(":shared:domain"))
                implementation(project(":shared:logging"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.auth)
                implementation(libs.android.gms.play.services.auth)
            }
        }
    }
}
