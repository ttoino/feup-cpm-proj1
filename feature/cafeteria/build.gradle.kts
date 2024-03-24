plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.cafeteria"
}

ksp {
    arg("compose-destinations.moduleName", "cafeteria")
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.coil.compose)
}
