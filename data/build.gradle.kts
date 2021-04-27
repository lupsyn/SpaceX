plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    implementation(Dependencies.jodaTime)

    testImplementation(Dependencies.jodaTime)
    testImplementation(project(ProjectModules.coreTestUtils))
}