plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.akkurate)
    implementation(libs.kotlinx.datetime)
}
