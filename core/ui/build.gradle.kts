plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.ui"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}
