plugins {
    id("com.android.application")
    id("com.google.secrets_gradle_plugin") version "0.5"
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.acompany.weightr"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
        }
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    coreLibraryDesugaring(Dependencies.AndroidTools.desugarJdk)

    implementation(Dependencies.Coil())
    implementation(Dependencies.PrettyTime())
    implementation(Dependencies.Timber())

    Dependencies.Accompanist.apply {
        implementation(coil)
        implementation(insets)
    }
    Dependencies.AndroidX.apply {
        implementation(activity)
        implementation(activityCompose)
        implementation(startup)
    }
    Dependencies.Compose.apply {
        implementation(material)
        implementation(materialIconsExtended)
        implementation(navigation)
        implementation(savable)
        implementation(tooling)
        androidTestImplementation(test)
    }
    Dependencies.Coroutines.apply {
        implementation(core)
        implementation(android)
    }
    Dependencies.Kotlin.apply {
        implementation(stdlib)
        implementation(reflect)
    }
}