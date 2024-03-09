import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("kotlin.jvm"))
            }
        }
    }
}