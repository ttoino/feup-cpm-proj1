import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("android.application"))
                apply(plugin("kotlin.android"))
                apply(plugin("hilt"))
                apply(plugin("ksp"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)

                defaultConfig.targetSdk = compileSdk
            }

            dependencies {
                // Hilt
                add("implementation", lib("hilt.android"))
                add("ksp", lib("hilt.compiler"))
                add("androidTestImplementation", lib("hilt.android.testing"))
                add("kspAndroidTest", lib("hilt.android.compiler"))

                // JUnit
                add("testImplementation", lib("junit"))
                add("androidTestImplementation", lib("androidx.test.ext"))
                add("androidTestImplementation", lib("androidx.test.espresso"))
            }
        }
    }
}
