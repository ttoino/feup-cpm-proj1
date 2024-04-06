plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.profile"
}

ksp {
    arg("compose-destinations.moduleName", "profile")
}

dependencies {
    implementation(libs.coil.compose)
}
