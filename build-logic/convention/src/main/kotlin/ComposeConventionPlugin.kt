import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<BaseExtension> {
                buildFeatures.apply {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = version("androidxComposeCompiler")
                }
            }

            dependencies {
                val bom = platform(lib("androidx.compose.bom"))
                add("implementation", bom)
                add("androidTestImplementation", bom)

                add("implementation", lib("androidx.compose.material3"))
                add("implementation", lib("androidx.compose.material.icons"))

                add("implementation", lib("androidx.compose.ui.tooling.preview"))
                add("debugImplementation", lib("androidx.compose.ui.tooling"))

                add("androidTestImplementation", lib("androidx.compose.ui.test.junit4"))
                add("debugImplementation", lib("androidx.compose.ui.test.manifest"))
            }
        }
    }
}