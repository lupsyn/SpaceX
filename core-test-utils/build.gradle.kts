plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(com.challenge.TestDependencies.kotlinxCoroutines)
    implementation(com.challenge.TestDependencies.AndroidX.espressoCore)
    implementation(com.challenge.TestDependencies.AndroidX.espressoContrib)
    implementation(com.challenge.Dependencies.jodaTime)
}