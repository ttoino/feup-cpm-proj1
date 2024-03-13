plugins {
    alias(libs.plugins.convention.android.application)
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
    implementation(projects.feature.auth)
    implementation(projects.feature.cafeteria)
    implementation(projects.feature.events)
    implementation(projects.feature.orders)
    implementation(projects.feature.tickets)

    implementation(projects.core.ui)
}
