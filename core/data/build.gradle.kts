plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.data"
}

dependencies {
    implementation(projects.core.local)
    implementation(projects.core.model)
    implementation(projects.core.network)

    implementation(libs.kotlinx.datetime)
    implementation(libs.timber)
}
