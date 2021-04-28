import com.challenge.android.plugin.BuildType

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.challengePlugin)
}

androidPlugin {
    buildType = BuildType.Library
}

dependencies {
    implementation(TestDependencies.kotlinxCoroutines)
    implementation(TestDependencies.AndroidX.espressoCore)
    implementation(TestDependencies.AndroidX.espressoContrib)
    implementation(Dependencies.jodaTime)
}