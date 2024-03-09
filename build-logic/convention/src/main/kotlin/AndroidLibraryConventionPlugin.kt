import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("android.library"))
                apply(plugin("kotlin.android"))
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }
        }
    }
}