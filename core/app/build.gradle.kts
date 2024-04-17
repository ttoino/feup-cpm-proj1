plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.app"
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.androidx.activity.compose)
    api(libs.androidx.splashscreen)
    implementation(libs.material)
}
