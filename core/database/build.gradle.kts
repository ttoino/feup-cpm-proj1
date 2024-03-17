plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.dagger)
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.datetime)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
}
