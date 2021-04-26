plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(project(com.challenge.ProjectModules.data))
    testImplementation(project(com.challenge.ProjectModules.coreTestUtils))

    api(com.challenge.Dependencies.Retrofit.retrofit)
    api(com.challenge.Dependencies.Retrofit.retrofitConverterGson)
    api(com.challenge.Dependencies.okHttpLoggingInterceptor)

    implementation(com.challenge.Dependencies.jodaTime)
    testImplementation(com.challenge.Dependencies.jodaTime)
}