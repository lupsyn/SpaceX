object ProjectModules {
    const val core = ":core"
    const val presentation = ":presentation"
    const val navigation = ":navigation"
    const val domain = ":domain"
    const val api = ":data-api"
    const val data = ":data"
    const val db = ":data-db"
    const val coreTestUtils = ":core-test-utils"
}

object AndroidSettings {
    const val compileSdk = 30
    const val buildTools = "30.0.3"
    const val minSdk = 26
    const val targetSdk = 30
    const val applicationId = "com.challenge.spacex"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val challengePlugin = "com.challenge.android.plugin"
    const val androidLibrary = "com.android.library"
}

object Versions {
    const val appCompat = "1.2.0"
    const val navigation = "2.3.5"
    const val constraintLayout = "2.0.4"
    const val materialDesign = "1.3.0"
    const val lifeCycle = "2.3.1"
    const val room = "2.3.0"
    const val androidxTest = "1.2.0"
    const val espresso = "3.2.0"
    const val androidxJunit = "1.1.1"
    const val junit = "4.12"
    const val junitPlatformRunner = "1.0.2"
    const val mockito = "3.9.0"
    const val gradle = "4.1.0"
    const val kotlin = "1.4.32"
    const val coreTesting = "1.1.1"
    const val retrofit = "2.9.0"
    const val retrofitConverterGson = "2.9.0"
    const val okhttpLoggingInterceptor = "4.9.1"
    const val picasso = "2.71828"
    const val jodaTime = "2.10.10"
    const val mockWebServer = "4.9.1"
    const val browser = "1.3.0"
    const val kotlinxCoroutines = "1.4.3"
}

object BuildDependencies {
    const val androidGradle =
        "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Dependencies {

    object AndroidX {
        const val appCompact =
            "androidx.appcompat:appcompat:${Versions.appCompat}"

        const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"

        const val fragmentKtx =
            "androidx.fragment:fragment-ktx:${Versions.appCompat}"
        const val coreKtx =
            "androidx.core:core-ktx:${Versions.appCompat}"

        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

        const val lifecycleLivedataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
        const val lifecycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycle}"

        const val archViewModel =
            "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifeCycle}"

        const val archViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"

        const val browser = "androidx.browser:browser:${Versions.browser}"

        object Room {
            val runtime = "androidx.room:room-runtime:${Versions.room}"
            val ktx = "androidx.room:room-ktx:${Versions.room}"
            val compiler = "androidx.room:room-compiler:${Versions.room}"
            val test = "androidx.room:room-testing:${Versions.room}"
        }

        object Navigation {
            const val fragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val uiKtx =
                "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }
    }

    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitConverterGson =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}"
    }

    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    const val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"

}

object TestDependencies {

    object AndroidX {
        const val core =
            "androidx.test:core:${Versions.androidxTest}"
        const val coreKtx =
            "androidx.test:core-ktx:${Versions.androidxTest}"
        const val runner =
            "androidx.test:runner:${Versions.androidxTest}"
        const val rules =
            "androidx.test:rules:${Versions.androidxTest}"
        const val espressoCore =
            "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib =
            "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.androidxTest}"
        const val junit =
            "androidx.test.ext:junit:${Versions.androidxJunit}"
        const val coreTesting =
            "android.arch.core:core-testing:${Versions.coreTesting}"
    }

    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"

    object JUnit {
        const val junit =
            "junit:junit:${Versions.junit}"
        const val junitPlatformRunner =
            "org.junit.platform:junit-platform-runner:${Versions.junitPlatformRunner}"
    }

    object Mockito {
        const val mockitoCore =
            "org.mockito:mockito-core:${Versions.mockito}"
        const val mockitoInline =
            "org.mockito:mockito-inline:${Versions.mockito}"
    }

    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
}
