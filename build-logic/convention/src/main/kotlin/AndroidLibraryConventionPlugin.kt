import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("android.library"))
                apply(plugin("kotlin.android"))
                apply(plugin("convention.base"))
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }

            dependencies {
                add("implementation", lib("timber"))
            }
        }
    }
}
