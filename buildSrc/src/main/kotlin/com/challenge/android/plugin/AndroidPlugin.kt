package com.challenge.android.plugin

import AndroidSettings
import Dependencies
import TestDependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

open class AndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<AndroidPluginExtension>("androidPlugin")

        with(project) {
            configurePlugins(extension.buildType)
            configureAndroid()
            configureDependencies()

            afterEvaluate {
                tasks {
                    withType<KotlinCompile> {
                        with(kotlinOptions) {
                            jvmTarget = "1.8"
                            freeCompilerArgs = listOf("-Xallow-result-return-type")
                        }
                    }
                }
            }
        }
    }

    private fun androidPlugins() = listOf("kotlin-android")

    private fun Project.configurePlugins(buildType: BuildType) = listOf(
        when (buildType) {
            BuildType.AndroidLibrary, BuildType.App -> androidPlugins()
            BuildType.Library -> listOf("kotlin")
        }, listOf("kotlin-kapt")
    ).flatten()
        .also { println("AndroidPlugin: applying plugins $it") }
        .forEach { plugins.apply(it) }

    private fun Project.configureAndroid() =
        extensions.getByType(BaseExtension::class.java).run {
            compileSdkVersion(AndroidSettings.compileSdk)
            buildToolsVersion(AndroidSettings.buildTools)
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

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            defaultConfig {
                minSdkVersion(AndroidSettings.minSdk)
                targetSdkVersion(AndroidSettings.targetSdk)

                testInstrumentationRunner = AndroidSettings.testInstrumentationRunner
            }

            testOptions {
                unitTests.isReturnDefaultValues = true
                animationsDisabled = true
            }

            lintOptions {
                isAbortOnError = false
            }
        }


    private fun Project.configureDependencies() = dependencies {
        fun implementation(definition: Any) = "implementation"(definition)
        fun testImplementation(definition: Any) = "testImplementation"(definition)
        fun androidTestImplementation(definition: Any) = "androidTestImplementation"(definition)

        implementation(kotlin("stdlib-jdk7"))
        testImplementation(kotlin("test"))

        implementation(Dependencies.kotlinxCoroutines)

        androidTestImplementation(TestDependencies.AndroidX.core)
        androidTestImplementation(TestDependencies.AndroidX.coreKtx)
        androidTestImplementation(TestDependencies.AndroidX.runner)
        androidTestImplementation(TestDependencies.AndroidX.rules)
        androidTestImplementation(TestDependencies.AndroidX.espressoCore)
        androidTestImplementation(TestDependencies.AndroidX.espressoContrib)
        androidTestImplementation(TestDependencies.AndroidX.junit)

        testImplementation(TestDependencies.kotlinxCoroutines)
        androidTestImplementation(TestDependencies.kotlinxCoroutines)

        testImplementation(TestDependencies.JUnit.junit)
        testImplementation(TestDependencies.JUnit.junitPlatformRunner)

        testImplementation(TestDependencies.Mockito.mockitoCore)
        testImplementation(TestDependencies.Mockito.mockitoInline)

        testImplementation(TestDependencies.AndroidX.coreTesting)
        testImplementation(Dependencies.jodaTime)
    }
}

open class AndroidPluginExtension {
    var buildType = BuildType.AndroidLibrary
}

enum class BuildType {
    Library,
    AndroidLibrary,
    App
}
