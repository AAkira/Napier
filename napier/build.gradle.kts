import dependencies.Dep
import dependencies.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply(from = rootProject.file("./gradle/publish.gradle.kts"))

val ideaActive = System.getProperty("idea.active") == "true"

kotlin {
    android {
        publishAllLibraryVariants()
    }
    js(BOTH) {
        browser()
        nodejs()
    }
    jvm()

    macosX64 {
        binaries {
            framework()
        }
    }
    if (ideaActive.not()) {
        // darwin
        ios {
            binaries {
                framework()
            }
        }
        watchos {
            binaries {
                framework()
            }
        }
        tvos {
            binaries {
                framework()
            }
        }

        // linux
        linuxX64 {
            binaries {
                staticLib()
            }
        }
        linuxArm32Hfp {
            binaries {
                staticLib()
            }
        }
        linuxArm64 {
            binaries {
                staticLib()
            }
        }
//        linuxMips32 {
//            binaries {
//                staticLib()
//            }
//        }
//        linuxMipsel32 {
//            binaries {
//                staticLib()
//            }
//        }
    } else {
        // darwin
        iosX64 {
            binaries {
                framework()
            }
        }
        watchosX64 {
            binaries {
                framework()
            }
        }
        tvosX64 {
            binaries {
                framework()
            }
        }

        // linux
        linuxX64 {
            binaries {
                staticLib()
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dep.Kotlin.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dep.Test.common)
                implementation(Dep.Test.annotation)
                implementation(Dep.Coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Dep.Test.jvm)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Dep.Kotlin.js)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(Dep.Test.js)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Dep.Kotlin.jvm)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Dep.Coroutines.core)
                implementation(Dep.Test.jvm)
            }
        }

        val darwinMain by creating {
            dependsOn(commonMain)
        }
        val darwinTest by creating {
            dependsOn(commonTest)
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val macosX64Main by getting {
            dependsOn(darwinMain)
        }
        val macosX64Test by getting {
            dependsOn(darwinTest)
        }
        if (ideaActive.not()) {
            // apple
            val iosMain by getting {
                dependsOn(darwinMain)
            }
            val iosTest by getting {
                dependsOn(darwinTest)
            }
            val watchosMain by getting {
                dependsOn(darwinMain)
            }
            val watchosTest by getting {
                dependsOn(darwinTest)
            }
            val tvosMain by getting {
                dependsOn(darwinMain)
            }
            val tvosTest by getting {
                dependsOn(darwinTest)
            }

            // linux
            val linuxX64Main by getting {
                dependsOn(nativeMain)
            }
            val linuxX64Test by getting {
                dependsOn(nativeTest)
            }
            val linuxArm32HfpMain by getting {
                dependsOn(nativeMain)
            }
            val linuxArm32HfpTest by getting {
                dependsOn(nativeTest)
            }
            val linuxArm64Main by getting {
                dependsOn(nativeMain)
            }
            val linuxArm64Test by getting {
                dependsOn(nativeTest)
            }
//            val linuxMips32Main by getting {
//                dependsOn(nativeMain)
//            }
//            val linuxMips32Test by getting {
//                dependsOn(nativeTest)
//            }
//            val linuxMipsel32Main by getting {
//                dependsOn(nativeMain)
//            }
//            val linuxMipsel32Test by getting {
//                dependsOn(nativeTest)
//            }
        } else {
            // apple
            val iosX64Main by getting {
                dependsOn(darwinMain)
            }
            val iosX64Test by getting {
                dependsOn(darwinTest)
            }
            val watchosX64Main by getting {
                dependsOn(darwinMain)
            }
            val watchosX64Test by getting {
                dependsOn(darwinTest)
            }
            val tvosX64Main by getting {
                dependsOn(darwinMain)
            }
            val tvosX64Test by getting {
                dependsOn(darwinTest)
            }

            // linux
            val linuxX64Main by getting {
                dependsOn(nativeMain)
            }
            val linuxX64Test by getting {
                dependsOn(nativeTest)
            }
        }
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
