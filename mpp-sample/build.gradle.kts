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
    androidTarget()
    js {
        browser()
    }
    jvm()

    // darwin
    if (ideaActive.not()) {
        // intel
        macosX64()
        iosX64()
        watchosX64()

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
            val iosX64Main by getting {
                dependsOn(darwinMain)
            }
            val watchosX64Main by getting {
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
    compileSdk = Versions.compileSdkVersion
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        namespace = "io.github.aakira.napier.mppsample"
        minSdk = Versions.minSdkVersion
    }

    lint {
        targetSdk = Versions.targetSdkVersion
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
