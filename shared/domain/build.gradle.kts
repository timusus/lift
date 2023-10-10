plugins {
    id("lift.library")
}


kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:data"))
                implementation(project(":shared:model"))
                implementation(project(":shared:logging"))

                implementation(libs.kotlinx.datetime)
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
