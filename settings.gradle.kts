pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "tiktek"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app:backend",
    ":app:cafeteria",
    ":app:main",
    ":app:tickets",

    "feature:auth",
    "feature:cafeteria",
    "feature:events",
    "feature:orders",
    "feature:order-scan",
    "feature:tickets",
    "feature:ticketscan",

    ":core:data",
    ":core:net",
    ":core:ui"
)
