import dependencies.Dep
import dependencies.Versions
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
}

version = "1.0.0"

kotlin {
    android()
    js {
        browser()
    }
    jvm()
    ios()
    macosX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dep.Kotlin.common)
                implementation(Dep.Coroutines.core)

                implementation(project(":napier"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dep.Kotlin.jvm)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(Dep.Kotlin.js)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Dep.Kotlin.jvm)
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val iosMain by getting {
            dependsOn(nativeMain)
        }
        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
    }

    cocoapods {
        summary = "CocoaPods library"
        homepage = "https://github.com/AAkira/Napier"
    }
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

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
