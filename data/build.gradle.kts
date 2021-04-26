plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(project(com.challenge.ProjectModules.domain))
    implementation(com.challenge.Dependencies.jodaTime)

    testImplementation(com.challenge.Dependencies.jodaTime)
    testImplementation(project(com.challenge.ProjectModules.coreTestUtils))
}