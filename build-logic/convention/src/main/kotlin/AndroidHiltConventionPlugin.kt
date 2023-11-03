import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Check if the project has the Android library plugin
            pluginManager.withPlugin("com.android.library") {
                applyPluginsAndDependencies(this@with)
            }

            // Or if the project has the Android application plugin
            pluginManager.withPlugin("com.android.application") {
                applyPluginsAndDependencies(this@with)
            }
        }
    }

    private fun applyPluginsAndDependencies(project: Project) {
        with(project.pluginManager) {
            apply("org.jetbrains.kotlin.kapt")
            apply("dagger.hilt.android.plugin")
        }

        project.extensions.configure(org.jetbrains.kotlin.gradle.plugin.KaptExtension::class.java) {
            correctErrorTypes = true
        }

        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
        project.dependencies {
            "implementation"(libs.findLibrary("hilt.android").get())
            "kapt"(libs.findLibrary("hilt.compiler").get())
            "kaptAndroidTest"(libs.findLibrary("hilt.compiler").get())
        }
    }
}