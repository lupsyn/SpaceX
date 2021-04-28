buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildDependencies.androidGradle)
        classpath(BuildDependencies.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}