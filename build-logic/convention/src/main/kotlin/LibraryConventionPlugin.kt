import com.android.build.gradle.LibraryExtension
import com.simplecityapps.lift.configureKotlinAndroid
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
                apply("com.rickclephas.kmp.nativecoroutines")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<LibraryExtension>("android") {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            (extensions.getByName("kotlin") as KotlinMultiplatformExtension).apply {

                applyDefaultHierarchyTemplate()

                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            configureCommonKotlinOptions(this@with)

                            // Set JVM target to 1.8
                            jvmTarget = JavaVersion.VERSION_17.toString()
                        }
                    }
                }

                listOf(
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach {
                    it.compilations.all {
                        kotlinOptions {
                            configureCommonKotlinOptions(this@with)
                        }
                    }
                }

                sourceSets {
                    commonTest.dependencies {
                        implementation(libs.findLibrary("kotlin.test").get())
                    }
                }
            }

            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }

        }

    }
}

private fun KotlinCommonOptions.configureCommonKotlinOptions(project: Project) {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    allWarningsAsErrors = warningsAsErrors.toBoolean()

    freeCompilerArgs = freeCompilerArgs + listOf(
        "-opt-in=kotlin.RequiresOptIn",
        // Enable experimental coroutines APIs, including Flow
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
        "-opt-in=kotlin.experimental.ExperimentalObjCName"
    )
}