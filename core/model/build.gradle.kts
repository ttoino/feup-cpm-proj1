plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

dependencies {
    api(libs.akkurate)
    implementation(libs.akkurate.ksp)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    ksp(libs.akkurate.ksp)
}
