plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.ui"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.compose.destinations.core)
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation(libs.androidx.appcompat)
}
