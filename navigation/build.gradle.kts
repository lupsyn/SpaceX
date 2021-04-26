plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(com.challenge.Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(com.challenge.Dependencies.AndroidX.Navigation.uiKtx)
}
