plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}