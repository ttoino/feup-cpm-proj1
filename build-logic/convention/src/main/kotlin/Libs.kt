import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.lib(name: String) = libs.findLibrary(name).get()

internal fun Project.version(name: String) = libs.findVersion(name).get().toString()

internal fun Project.plugin(name: String) = libs.findPlugin(name).get().get().pluginId
