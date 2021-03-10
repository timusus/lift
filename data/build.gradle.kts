plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.squareup.sqldelight")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Dependencies.Moshi.kotlin)
    implementation(Dependencies.Timber())
    Dependencies.Coroutines.apply {
        implementation(core)
        implementation(android)
    }
    Dependencies.SqlDelight.apply {
        implementation(sqlDelightDriver)
        implementation(sqlDelightCoroutines)
    }
    Dependencies.Dagger.apply {
        kapt(hiltKapt)
        implementation(hilt)
    }
}