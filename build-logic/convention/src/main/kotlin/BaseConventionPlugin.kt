import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class BaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin("ktlint"))
            }

            extensions.configure<KtlintExtension> {
                filter {
                    exclude { it.file.absolutePath.contains("/build/generated/") }
                }
            }
        }
    }
}
