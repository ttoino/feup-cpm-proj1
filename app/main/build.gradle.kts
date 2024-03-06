plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.compose)
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
//    implementation(projects.feature.auth)
//    implementation(projects.feature.cafeteria)
//    implementation(projects.feature.events)
//    implementation(projects.feature.orders)
//    implementation(projects.feature.tickets)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
}
