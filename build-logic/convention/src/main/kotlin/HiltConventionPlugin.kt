import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("ksp"))
                apply(plugin("hilt"))
            }

            dependencies {
                add("implementation", lib("hilt.android"))
                add("ksp", lib("hilt.compiler"))
                add("androidTestImplementation", lib("hilt.android.testing"))
                add("kspAndroidTest", lib("hilt.android.compiler"))
            }
        }
    }
}
