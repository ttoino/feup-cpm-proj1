plugins {
    `kotlin-dsl`
}

group = "pt.up.fe.cpm.tiktek.buildlogic"

dependencies {
    compileOnly(libs.android.ddmlib)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.repository)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("base") {
            id = "tiktek.base"
            implementationClass = "BaseConventionPlugin"
        }
        register("androidApplication") {
            id = "tiktek.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeature") {
            id = "tiktek.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = "tiktek.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "tiktek.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("dagger") {
            id = "tiktek.dagger"
            implementationClass = "DaggerConventionPlugin"
        }
        register("hilt") {
            id = "tiktek.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("library") {
            id = "tiktek.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}
