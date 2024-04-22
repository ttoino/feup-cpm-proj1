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
    implementation(projects.core.ui)

    implementation(libs.coil.compose)
    implementation(project(":core:model"))
    implementation(project(":core:data"))
}
