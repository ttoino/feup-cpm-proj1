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
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
    implementation(project(":core:ui"))
}
