import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    id("com.google.secrets_gradle_plugin") version "0.6.1"
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.protobuf") version "0.8.12"
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
        testInstrumentationRunner = "com.lift.android.dagger.CustomTestRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/AL2.0")
    }
}

dependencies {

    androidTestImplementation("androidx.test:runner:1.4.0")

    implementation(project(":data"))
    coreLibraryDesugaring(Dependencies.AndroidTools.desugarJdk)

    implementation(Dependencies.Coil())
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
        implementation(datastorePreferences)
        implementation(datastore)
    }
    Dependencies.Compose.apply {
        implementation(material)
        implementation(materialIconsExtended)
        implementation(savable)
        implementation(tooling)
        implementation(ui)
        implementation(foundation)
        androidTestImplementation(test)
        debugImplementation(testManifest)
    }
    Dependencies.Compose.Navigation.apply {
        implementation(hilt)
        implementation(navigation)
    }
    Dependencies.Dagger.apply {
        implementation(hilt)
        kapt(hiltKapt)
        androidTestImplementation(hiltTest)
        kaptAndroidTest(hiltTestKapt)
    }
    Dependencies.Kotlin.apply {
        implementation(stdlib)
        implementation(reflect)
    }
    Dependencies.Moshi.apply {
        implementation(kotlin)
        implementation(adapters)
    }
    implementation(Dependencies.ProtoBuf())
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.15.0"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}