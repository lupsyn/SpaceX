import com.challenge.android.plugin.BuildType

plugins {
    id("com.android.application")
    id("com.challenge.android.plugin")
}

androidPlugin {
    buildType = BuildType.App
}

android {
    defaultConfig {
        applicationId = "com.challenge.spacex"
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            buildConfigField("Integer", "PORT", "8080")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
}

dependencies {
    implementation(project(com.challenge.ProjectModules.core))
    implementation(project(com.challenge.ProjectModules.navigation))
    implementation(com.challenge.Dependencies.AndroidX.materialDesign)
    implementation(com.challenge.Dependencies.AndroidX.appCompact)
    implementation(com.challenge.Dependencies.AndroidX.fragmentKtx)
    implementation(com.challenge.Dependencies.AndroidX.lifecycleLivedataKtx)
    annotationProcessor(com.challenge.Dependencies.AndroidX.lifecycleCompiler)
    implementation(com.challenge.Dependencies.AndroidX.browser)

    implementation(com.challenge.Dependencies.picasso)
    implementation(com.challenge.Dependencies.AndroidX.constraintlayout)
    implementation(com.challenge.Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(com.challenge.Dependencies.AndroidX.Navigation.uiKtx)

    androidTestImplementation(com.challenge.TestDependencies.AndroidX.runner)
    androidTestImplementation(com.challenge.TestDependencies.AndroidX.rules)
    androidTestImplementation(com.challenge.TestDependencies.mockWebServer)
    androidTestImplementation(com.challenge.TestDependencies.AndroidX.core)
    androidTestImplementation(com.challenge.TestDependencies.AndroidX.coreKtx)
}
