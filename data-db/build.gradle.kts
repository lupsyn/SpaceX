import com.challenge.android.plugin.BuildType

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.challengePlugin)
}

androidPlugin {
    buildType = BuildType.Library
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