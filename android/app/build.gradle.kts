plugins {
    id("lift.android.application")
    id("lift.android.application.compose")
    alias(libs.plugins.kotlin.android)
    id("lift.android.hilt")
    alias(libs.plugins.secrets)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.simplecityapps.lift.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.simplecityapps.lift.android"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared:common"))
    implementation(project(":shared:logging"))
    implementation(project(":shared:data:database"))
    implementation(project(":shared:data:network"))
    implementation(project(":shared:data:repository"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:auth"))

//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth)
//    implementation(libs.android.gms.play.services.auth)

    androidTestImplementation(libs.androidx.navigation.testing)

    debugImplementation(libs.androidx.compose.ui.testManifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.android.gms.play.services.maps)
    implementation(libs.android.gms.play.services.location)
    implementation(libs.android.maps.compose)
    implementation(libs.android.maps.compose.utils)
    implementation(libs.android.maps.compose.widgets)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.window.manager)

    implementation(libs.kotlinx.datetime)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(kotlin("test"))
    testImplementation(kotlin("test"))
    testImplementation(libs.junit5.jupiter.api)
    testImplementation(libs.junit5.jupiter.params)
    testRuntimeOnly(libs.junit5.jupiter.engine)
    testImplementation(libs.mockk)

}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}