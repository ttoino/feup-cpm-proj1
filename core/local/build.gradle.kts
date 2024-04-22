plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
//    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.core.local"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    ksp(libs.androidx.room.compiler)
}
