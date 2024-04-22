plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.ui"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.compose.destinations.core)
}
