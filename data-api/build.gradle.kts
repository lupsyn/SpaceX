plugins {
    id("com.android.library")
    id("com.challenge.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.data))
    testImplementation(project(ProjectModules.coreTestUtils))

    api(Dependencies.Retrofit.retrofit)
    api(Dependencies.Retrofit.retrofitConverterGson)
    api(Dependencies.okHttpLoggingInterceptor)

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}