import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinCocoapods)
}

version = "1.0.0"

yarn.lockFileDirectory = file("kotlin-js-store")

kotlin {
    jvmToolchain(17)

    androidLibrary {
        namespace = "io.github.aakira.napier.mppsample"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    js {
        browser()
    }
    jvm()

    // darwin
    macosX64()
    macosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosX64()
    watchosSimulatorArm64()

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            export(project(":napier"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)

            api(project(":napier"))
        }
    }

    cocoapods {
        summary = "CocoaPods library"
        homepage = "https://github.com/AAkira/Napier"

        ios.deploymentTarget = "15.0"
        osx.deploymentTarget = "11.0"
        watchos.deploymentTarget = "8.0"
    }
}
