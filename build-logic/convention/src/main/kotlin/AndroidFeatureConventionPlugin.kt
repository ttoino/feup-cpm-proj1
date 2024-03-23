import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("convention.base"))
                apply(plugin("convention.android.library"))
                apply(plugin("convention.compose"))
                apply(plugin("convention.hilt"))
            }

            dependencies {
                add("implementation", lib("androidx.core.ktx"))
                add("implementation", lib("androidx.hilt.navigation.compose"))
                add("implementation", lib("androidx.lifecycle.runtime.ktx"))
                add("implementation", lib("androidx.lifecycle.viewmodel.compose"))
                add("implementation", lib("androidx.navigation.compose"))
                add("implementation", lib("compose.destinations.core"))
                add("ksp", lib("compose.destinations.ksp"))
            }
        }
    }
}
