import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("android.application"))
                apply(plugin("kotlin.android"))
                apply(plugin("convention.base"))
                apply(plugin("convention.compose"))
                apply(plugin("convention.hilt"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)

                defaultConfig.targetSdk = compileSdk
            }

            dependencies {
                add("implementation", project(":core:app"))

                add("implementation", lib("timber"))
                add("implementation", lib("androidx.activity.compose"))
                add("implementation", lib("androidx.core.ktx"))
                add("implementation", lib("androidx.hilt.navigation.compose"))
                add("implementation", lib("androidx.lifecycle.runtime.ktx"))
                add("implementation", lib("androidx.lifecycle.viewmodel.compose"))
                add("implementation", lib("androidx.navigation.compose"))
                add("implementation", lib("compose.destinations.core"))
                add("ksp", lib("compose.destinations.ksp"))

                // JUnit
                add("testImplementation", lib("junit"))
                add("androidTestImplementation", lib("androidx.test.ext"))
                add("androidTestImplementation", lib("androidx.test.espresso"))
            }
        }
    }
}
