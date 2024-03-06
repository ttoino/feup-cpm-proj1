import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroid(extension: CommonExtension<*, *, *, *, *>) {
    extension.apply {
        namespace = "pt.up.fe.cpm.tiktek"
        compileSdk = version("sdk").toInt()

        defaultConfig {
            minSdk = version("minSdk").toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
