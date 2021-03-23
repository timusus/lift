object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha11"

    object Accompanist {
        private const val version = "0.6.2"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:$version"
    }

    object AndroidTools {
        const val desugarJdk = "com.android.tools:desugar_jdk_libs:1.0.9"
    }

    object AndroidX {
        private const val lifecycleVersion = "2.3.0"

        const val activity = "androidx.activity:activity:1.3.0-SNAPSHOT"
        const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha03"
        const val ktx = "androidx.core:core-ktx:1.5.0-beta01"
        const val core = "androidx.core:core:1.5.0-alpha03"
        const val datastore = "androidx.datastore:datastore-preferences:1.0.0-alpha06"
        const val palette = "androidx.palette:palette:1.0.0"
        const val work = "androidx.work:work-runtime-ktx:2.5.0"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-beta01"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
        const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"
    }

    object Coil {
        operator fun invoke() = "io.coil-kt:coil:1.1.1"
    }

    object Compose {
        const val version = "1.0.0-beta02"
        const val snapshot = "7141639"
        const val snapshotUrl =
            "https://androidx.dev/snapshots/builds/$snapshot/artifacts/repository/"

        const val compiler = "androidx.compose.compiler:compiler:$version"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
        const val material = "androidx.compose.material:material:$version"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val savable = "androidx.compose.runtime:runtime-saveable:$version"
        const val test = "androidx.compose.ui:ui-test:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"

        object Navigation {
            private const val hiltVersion = "1.0.0-alpha01"
            private const val navigationVersion = "1.0.0-alpha09"
            const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
            const val hilt = "androidx.hilt:hilt-navigation-compose:$hiltVersion"
        }
    }

    object Coroutines {
        private const val version = "1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Dagger {
        private const val hiltVersion = "2.33-beta"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltKapt = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object Kotlin {
        private const val version = Versions.kotlin
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object LeakCanary {
        operator fun invoke() = "com.squareup.leakcanary:leakcanary-android:2.4"
    }

    object Moshi {
        private const val version = "1.11.0"
        const val core = "com.squareup.moshi:moshi:$version"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        const val adapters = "com.squareup.moshi:moshi-adapters:$version"
    }

    object Okhttp {
        private const val version = "5.0.0-alpha.2"
        const val core = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object PlayServices {
        const val gradlePlugin = "com.google.gms:google-services:4.3.5"
        const val ossChecker = "com.google.android.gms:play-services-oss-licenses:17.0.0"
        const val ossCheckerPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.2"
    }

    object PrettyTime {
        operator fun invoke() = "org.ocpsoft.prettytime:prettytime:5.0.0.Final"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$version"
        const val scalars = "com.squareup.retrofit2:converter-scalars:$version"
    }

    object SqlDelight {
        const val sqlDelightPlugin = "com.squareup.sqldelight:gradle-plugin:1.4.4"
        const val sqlDelightDriver = "com.squareup.sqldelight:android-driver:1.4.4"
        const val sqlDelightCoroutines = "com.squareup.sqldelight:coroutines-extensions-jvm:1.4.4"
    }

    object Timber {
        operator fun invoke() = "com.jakewharton.timber:timber:4.7.1"
    }
}