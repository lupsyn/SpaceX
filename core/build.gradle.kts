plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(com.challenge.Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(com.challenge.Dependencies.AndroidX.Navigation.uiKtx)
    implementation(com.challenge.Dependencies.AndroidX.lifecycleLivedataKtx)

   annotationProcessor(com.challenge.Dependencies.AndroidX.lifecycleCompiler)
    annotationProcessor(com.challenge.Dependencies.AndroidX.archViewModel)

    implementation(com.challenge.Dependencies.AndroidX.archViewModelKtx)
    implementation(com.challenge.Dependencies.AndroidX.archViewModel)
}