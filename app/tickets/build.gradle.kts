plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        applicationId = "pt.up.fe.cpm.tiktek.tickets"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.zxing)
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
}
