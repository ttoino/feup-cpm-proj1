plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.hilt)
}

android {
    defaultConfig {
        applicationId = "pt.up.fe.cpm.tiktek"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    implementation("androidx.biometric:biometric:1.1.0")

    implementation(projects.feature.auth)
    implementation(projects.feature.cafeteria)
    implementation(projects.feature.events)
    implementation(projects.feature.orders)
    implementation(projects.feature.profile)
    implementation(projects.feature.tickets)

    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work)
}
