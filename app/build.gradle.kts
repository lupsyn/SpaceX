import com.challenge.android.plugin.BuildType

plugins {
    id(Plugins.androidApplication)
    id(Plugins.challengePlugin)
    id(Plugins.kotlinAndroid)
}

android {
    defaultConfig {
        applicationId = AndroidSettings.applicationId
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.putIfAbsent("room.schemaLocation", "$projectDir/schemas")
            }
        }
//        testInstrumentationRunner("com.challenge.spacex.main.TestInstrumentationRunner")
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    buildFeatures.viewBinding = true
}

androidPlugin {
    buildType = BuildType.App
}

dependencies {
    implementation(project(ProjectModules.core))
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.data))
    implementation(project(ProjectModules.api))
    implementation(project(ProjectModules.db))
    implementation(project(ProjectModules.presentation))
    implementation(project(ProjectModules.navigation))
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
