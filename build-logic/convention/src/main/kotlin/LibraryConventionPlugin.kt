import com.android.build.gradle.LibraryExtension
import com.simplecityapps.shuttle3.configureKotlinAndroid
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.internal.impldep.com.jcraft.jsch.ConfigRepository.defaultConfig
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension>("android") {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }

            (extensions.getByName("kotlin") as KotlinMultiplatformExtension).apply {
                android {
                    compilations.all {
                        kotlinOptions {
                            configureCommonKotlinOptions(this@with)

                            // Set JVM target to 1.8
                            jvmTarget = JavaVersion.VERSION_1_8.toString()
                        }
                    }
                }

                listOf(
                    iosX64(),
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
                    val commonMain by getting
                    val commonTest by getting {
                        dependencies {
                            implementation(kotlin("test"))
                        }
                    }

                    val androidMain by getting
                    val androidTest by getting

                    val iosX64Main by getting
                    val iosArm64Main by getting
                    val iosSimulatorArm64Main by getting
                    val iosMain by creating {
                        dependsOn(commonMain)
                        iosX64Main.dependsOn(this)
                        iosArm64Main.dependsOn(this)
                        iosSimulatorArm64Main.dependsOn(this)
                    }
                    val iosX64Test by getting
                    val iosArm64Test by getting
                    val iosSimulatorArm64Test by getting
                    val iosTest by creating {
                        dependsOn(commonTest)
                        iosX64Test.dependsOn(this)
                        iosArm64Test.dependsOn(this)
                        iosSimulatorArm64Test.dependsOn(this)
                    }
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
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
        "-opt-in=kotlin.Experimental",
        // Enable experimental kotlinx serialization APIs
        "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    )
}