plugins {
    alias(libs.plugins.convention.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.cafeteria"
}

ksp {
    arg("compose-destinations.moduleName", "cafeteria")
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(libs.kotlinx.datetime)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.zxing)
}
