plugins {
    alias(libs.plugins.convention.android.application)
}

android {
    defaultConfig {
        applicationId = "pt.up.fe.cpm.tiktek.cafeteria"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.feature.orderScan)
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")
}
