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
    implementation(libs.coil.compose)
}
