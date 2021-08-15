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

    ios {
        binaries {
            framework()
        }
    }
    macosX64 {
        binaries {
            framework()
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

        val nativeMain = if (ideaActive) {
            val nativeMain by getting {
                dependsOn(commonMain)
            }
            nativeMain
        } else {
            val nativeMain by creating {
                dependsOn(commonMain)
            }
            nativeMain
        }
        val nativeTest = if (ideaActive) {
            val nativeTest by getting {
                dependsOn(commonTest)
            }
            nativeTest
        } else {
            val nativeTest by creating {
                dependsOn(commonTest)
            }
            nativeTest
        }

        val iosMain by getting {
            dependencies {
            }
        }
        val iosTest by getting {
            dependencies {
            }
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
        val macosX64Test by getting {
            dependsOn(nativeTest)
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
