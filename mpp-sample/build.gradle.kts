import dependencies.Dep
import dependencies.Versions

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

    // darwin
    if (isAppleSilicon) {
        // apple silicon
        macosArm64()
        iosSimulatorArm64()
        watchosSimulatorArm64()
    } else {
        // intel
        macosX64()
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
        if (isAppleSilicon) {
            val macosArm64Main by getting {
                dependsOn(darwinMain)
            }
            val iosSimulatorArm64Main by getting {
                dependsOn(darwinMain)
            }
            val watchosSimulatorArm64Main by getting {
                dependsOn(darwinMain)
            }
        } else {
            val macosX64Main by getting {
                dependsOn(darwinMain)
            }
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
