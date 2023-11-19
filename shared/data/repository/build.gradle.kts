plugins {
    id("lift.library")
    id("lift.android.hilt")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(project(":shared:common"))
            implementation(project(":shared:data:auth"))
            implementation(project(":shared:data:database"))
            implementation(project(":shared:data:firestore"))
            implementation(project(":shared:data:local-json"))
            implementation(project(":shared:data:preferences"))
            implementation(project(":shared:domain"))
            implementation(project(":shared:logging"))
        }
    }
}
