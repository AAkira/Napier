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

    if (ideaActive) {
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
        if (ideaActive) {
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
