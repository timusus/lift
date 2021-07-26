plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.squareup.sqldelight")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

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

sqldelight {
    database("Database") {
        schemaOutputDirectory = file("src/main/sqldelight/databases")
        verifyMigrations = true
    }
}

dependencies {
    implementation(Dependencies.Timber())
    Dependencies.Moshi.apply {
        implementation(kotlin)
        implementation(adapters)
    }
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