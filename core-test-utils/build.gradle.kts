plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(TestDependencies.kotlinxCoroutines)
    implementation(TestDependencies.AndroidX.espressoCore)
    implementation(TestDependencies.AndroidX.espressoContrib)
    implementation(Dependencies.jodaTime)
}