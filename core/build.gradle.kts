plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
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