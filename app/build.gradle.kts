plugins {
    id("com.android.application")
    id("com.google.secrets_gradle_plugin") version "0.6.1"
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.acompany.lift"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
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
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":data"))
    coreLibraryDesugaring(Dependencies.AndroidTools.desugarJdk)

//    implementation(Dependencies.Coil())
    implementation(Dependencies.PrettyTime())
    implementation(Dependencies.Timber())

    Dependencies.Accompanist.apply {
        implementation(insets)
    }
    Dependencies.AndroidX.apply {
        implementation(activity)
        implementation(activityCompose)
        implementation(lifecycleViewModelKtx)
        implementation(lifecycleViewModelCompose)
        implementation(viewModelSavedState)
    }
    Dependencies.Compose.apply {
        implementation(material)
        implementation(materialIconsExtended)
        implementation(savable)
        implementation(tooling)
        implementation(ui)
        implementation(foundation)
        androidTestImplementation(test)
    }
    Dependencies.Compose.Navigation.apply {
        implementation(hilt)
        implementation(navigation)
    }
    Dependencies.Dagger.apply {
        kapt(hiltKapt)
        implementation(hilt)
    }
    Dependencies.Kotlin.apply {
        implementation(stdlib)
        implementation(reflect)
    }
    Dependencies.Moshi.apply {
        implementation(kotlin)
        implementation(adapters)
    }
}