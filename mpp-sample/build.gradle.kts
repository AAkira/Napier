import dependencies.Dep
import dependencies.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
}

version = "1.0.0"

val ideaActive = System.getProperty("idea.active") == "true"

kotlin {
    android()
    js {
        browser()
    }
    jvm()

    // darwin
    macosX64()
    if (ideaActive.not()) {
        ios()
        watchos()
    } else {
        iosX64()
        watchosX64()
    }

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

        // apple
        val darwinMain by creating {
            dependsOn(commonMain)
        }
        val macosX64Main by getting {
            dependsOn(darwinMain)
        }
        if (ideaActive.not()) {
            val iosMain by getting {
                dependsOn(darwinMain)
            }
            val watchosMain by getting {
                dependsOn(darwinMain)
            }
        } else {
            val iosX64Main by getting {
                dependsOn(darwinMain)
            }
            val watchosX64Main by getting {
                dependsOn(darwinMain)
            }
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
