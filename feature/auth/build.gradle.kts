plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.auth"
}

ksp {
    arg("compose-destinations.moduleName", "auth")
}

dependencies {
    implementation(projects.core.ui)
}
