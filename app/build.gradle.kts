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
    implementation(Dependencies.AndroidX.materialDesign)
    implementation(Dependencies.AndroidX.appCompact)
    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    implementation(Dependencies.AndroidX.browser)

    implementation(Dependencies.picasso)
    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)

    androidTestImplementation(TestDependencies.AndroidX.runner)
    androidTestImplementation(TestDependencies.AndroidX.rules)
    androidTestImplementation(TestDependencies.mockWebServer)
    androidTestImplementation(TestDependencies.AndroidX.core)
    androidTestImplementation(TestDependencies.AndroidX.coreKtx)
}
