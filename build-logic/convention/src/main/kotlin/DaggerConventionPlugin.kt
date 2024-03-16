import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DaggerConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("ksp"))
            }

            dependencies {
                add("implementation", lib("dagger"))
                add("ksp", lib("dagger.compiler"))
            }
        }
    }
}