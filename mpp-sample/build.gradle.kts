import dependencies.Dep
import dependencies.Versions
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
}

version = "1.0.0"

yarn.lockFileDirectory = file("kotlin-js-store")

kotlin {
    android()
    js {
        browser()
    }
    jvm()

    // darwin
    if (ideaActive.not()) {
        // intel
        macosX64()
        ios()
        watchos()

        // apple silicon
        macosArm64()
        iosSimulatorArm64()
        watchosSimulatorArm64()
    } else {
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
    }
    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            export(project(":napier"))
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dep.Kotlin.common)
                implementation(Dep.Coroutines.core)

                implementation(project(":napier"))
                api(project(":napier"))
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

        // darwin
        val darwinMain by creating {
            dependsOn(commonMain)
        }
        // darwin
        if (ideaActive.not()) {
            // intel
            val macosX64Main by getting {
                dependsOn(darwinMain)
            }
            val iosMain by getting {
                dependsOn(darwinMain)
            }
            val watchosMain by getting {
                dependsOn(darwinMain)
            }

            // apple silicon
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
            if (isAppleSilicon) {
                // apple silicon
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
                // intel
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
