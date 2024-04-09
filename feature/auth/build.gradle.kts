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
    implementation(projects.core.data)
    implementation(projects.core.ui)

    implementation(libs.kotlinx.datetime)
}
