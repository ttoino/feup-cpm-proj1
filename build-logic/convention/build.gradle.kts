import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "pt.up.fe.cpm.tiktek.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "tiktek.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
    plugins {
        register("androidApplication") {
            id = "tiktek.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
    plugins {
        register("compose") {
            id = "tiktek.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
}
