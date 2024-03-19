plugins {
    alias(libs.plugins.convention.android.feature)
}

android {
    namespace = "pt.up.fe.cpm.tiktek.feature.events"
}

ksp {
    arg("compose-destinations.moduleName", "events")
}

dependencies {
}
