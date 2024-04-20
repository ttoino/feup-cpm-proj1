plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.events"
}

ksp {
    arg("compose-destinations.moduleName", "events")
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
}
