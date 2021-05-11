import dependencies.Dep
import dependencies.Versions

plugins {
    id("com.android.application")
    kotlin("android")

    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode(Versions.androidVersionCode)
        versionName(Versions.androidVersionName)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        exclude("META-INF/kotlinx-coroutines-core.kotlin_module")
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    implementation(project(":napier"))
    implementation(project(":mpp-sample"))
    // write the below in your project, instead of above
    // implementation "io.github.aakira:napier:[version name]"

    implementation(Dep.Kotlin.jvm)
    implementation(Dep.Coroutines.core)

    implementation(Dep.Android.appCompat)
    implementation(Dep.Android.constraintLayout)

    // for crashlytics sample
    implementation(platform(Dep.Firebase.platform))
    implementation(Dep.Firebase.crashlytics)
}
