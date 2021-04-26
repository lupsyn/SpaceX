rootProject.name = "SpaceX"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ":domain",
    ":core-test-utils",
    ":core",
    ":data",
    ":data-db",
    ":data-api",
    ":navigation"
)
