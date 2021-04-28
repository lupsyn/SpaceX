import com.challenge.android.plugin.BuildType

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.challengePlugin)
}

androidPlugin {
    buildType = BuildType.Library
}

dependencies {
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)

    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    annotationProcessor(Dependencies.AndroidX.archViewModel)

    implementation(Dependencies.AndroidX.archViewModelKtx)
    implementation(Dependencies.AndroidX.archViewModel)
}