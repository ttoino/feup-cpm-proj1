plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.tickets"
}

ksp {
    arg("compose-destinations.moduleName", "tickets")
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
}
