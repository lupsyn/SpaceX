plugins {
    id(Plugins.androidLibrary)
    id(Plugins.challengePlugin)
}

dependencies {
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.core))
    testImplementation(project(ProjectModules.coreTestUtils))

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)

    implementation(Dependencies.jodaTime)
    implementation(Dependencies.Retrofit.retrofit)

    testImplementation(Dependencies.jodaTime)
}