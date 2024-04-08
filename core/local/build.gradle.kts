plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.local"
}

dependencies {
    implementation(projects.core.model)
}
