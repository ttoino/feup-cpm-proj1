plugins {
    alias(libs.plugins.convention.android.feature)
    alias(libs.plugins.kotlin.serialization)
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

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.zxing)
}
