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
    js {
        browser()
        nodejs()
    }
    jvm()

    macosX64 {
        binaries {
            framework()
        }
    }
    if(ideaActive.not()) {
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

        val appleMain by creating {
            dependsOn(commonMain)
        }
        val appleTest by creating {
            dependsOn(commonTest)
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val macosX64Main by getting {
            dependsOn(appleMain)
        }
        val macosX64Test by getting {
            dependsOn(appleTest)
        }
        if(ideaActive.not()) {
            // darwin
            val iosMain by getting {
                dependsOn(appleMain)
            }
            val iosTest by getting {
                dependsOn(appleTest)
            }
            val watchosMain by getting {
                dependsOn(appleMain)
            }
            val watchosTest by getting {
                dependsOn(appleTest)
            }
            val tvosMain by getting {
                dependsOn(appleMain)
            }
            val tvosTest by getting {
                dependsOn(appleTest)
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
        } else {
            // darwin
            val iosX64Main by getting {
                dependsOn(appleMain)
            }
            val iosX64Test by getting {
                dependsOn(appleTest)
            }
            val watchosX64Main by getting {
                dependsOn(appleMain)
            }
            val watchosX64Test by getting {
                dependsOn(appleTest)
            }
            val tvosX64Main by getting {
                dependsOn(appleMain)
            }
            val tvosX64Test by getting {
                dependsOn(appleTest)
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
