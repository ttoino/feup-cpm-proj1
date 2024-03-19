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
        register("androidApplication") {
            id = "tiktek.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
    plugins {
        register("androidFeature") {
            id = "tiktek.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
    plugins {
        register("androidLibrary") {
            id = "tiktek.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
    plugins {
        register("compose") {
            id = "tiktek.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
    plugins {
        register("dagger") {
            id = "tiktek.dagger"
            implementationClass = "DaggerConventionPlugin"
        }
    }
    plugins {
        register("hilt") {
            id = "tiktek.hilt"
            implementationClass = "HiltConventionPlugin"
        }
    }
    plugins {
        register("library") {
            id = "tiktek.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}
