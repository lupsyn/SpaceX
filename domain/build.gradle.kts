import com.challenge.android.plugin.BuildType

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.challengePlugin)
}

androidPlugin {
    buildType = BuildType.Library
}

dependencies {
    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
    testImplementation(project(ProjectModules.coreTestUtils))
}