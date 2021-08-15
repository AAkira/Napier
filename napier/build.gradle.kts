import dependencies.Dep
import dependencies.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply(from = rootProject.file("./gradle/publish.gradle.kts"))

enum class HostOs {
    LINUX, WINDOWS, MAC
}

val napierPrimaryDevelopmentOs: String? by project

val primaryDevelopmentOs: HostOs = if (napierPrimaryDevelopmentOs != null) {
    println("Selected dev OS: $napierPrimaryDevelopmentOs")
    when (napierPrimaryDevelopmentOs) {
        "linux" -> HostOs.LINUX
        "windows" -> HostOs.WINDOWS
        "mac" -> HostOs.MAC
        else -> throw GradleException(
            "Invalid development environment OS select: " +
                    "$napierPrimaryDevelopmentOs. Only linux, windows and mac are supported at the moment"
        )
    }
} else {
    HostOs.LINUX
}

val ideaActive = System.getProperty("idea.active") == "true"

fun getHostOsName(): HostOs {
    val target = System.getProperty("os.name")
    if (target == "Linux") return HostOs.LINUX
    if (target.startsWith("Windows")) return HostOs.WINDOWS
    if (target.startsWith("Mac")) return HostOs.MAC
    throw GradleException("Unknown OS: $target")
}

kotlin {
    val hostOs = getHostOsName()
    println("Host os name $hostOs")

    if (ideaActive) {
        when (hostOs) {
            HostOs.LINUX -> linuxX64("native")
            HostOs.MAC -> macosX64("native")
            HostOs.WINDOWS -> mingwX64("native")
        }
    }

    android {
        publishAllLibraryVariants()
    }
//    ios {
//        binaries {
//            framework()
//        }
//    }
    js {
        browser()
        nodejs()
    }
    jvm()

    if (hostOs == HostOs.LINUX) {

        linuxX64("linux") {
            binaries {
                staticLib {
                }
            }
        }

        if (ideaActive.not()) {
            linuxArm32Hfp {
                binaries {
                    staticLib {
                    }
                }
            }

            linuxArm64 {
                binaries {
                    staticLib {
                    }
                }
            }
        }
    }

    iosX64("ios") {
        binaries {
            framework {
            }
        }
    }
    iosArm64("ios64Arm") {
        binaries {
            framework {
            }
        }
    }

    iosArm32("ios32Arm") {
        binaries {
            framework {
            }
        }
    }
    macosX64 {
        binaries {
            framework {
            }
        }
    }

    tvos {
        binaries {
            framework {
            }
        }
    }
    if (ideaActive.not()) {
        watchos {
            binaries {
                framework {
                }
            }
        }
    }

    watchosX86 {
        binaries {
            framework {
            }
        }
    }

    mingwX64 {
        binaries {
            staticLib {
            }
        }
    }
    if (ideaActive.not()) {
        mingwX86 {
            binaries {
                staticLib {
                }
            }
        }
    }

    println(targets.names)

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
//        val iosMain by getting {
//            dependencies {
//            }
//        }
//        val iosTest by getting {
//            dependencies {
//            }
//        }

        if (hostOs == primaryDevelopmentOs) {
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

        if (hostOs == HostOs.LINUX) {

            val linuxMain by getting {
                dependsOn(nativeMain)
            }
            val linuxTest by getting {
                dependsOn(nativeTest)
            }

            if (ideaActive.not()) {

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
            }
        }
        val iosMain by getting {
            dependsOn(nativeMain)
        }
        val iosTest by getting {
            dependsOn(nativeTest)
        }

        val ios64ArmMain by getting {
            dependsOn(nativeMain)
        }
        val ios64ArmTest by getting {
            dependsOn(nativeTest)
        }

        val ios32ArmMain by getting {
            dependsOn(nativeMain)
        }
        val ios32ArmTest by getting {
            dependsOn(nativeTest)
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
        val macosX64Test by getting {
            dependsOn(nativeTest)
        }

        val tvosMain by getting {
            dependsOn(nativeMain)
        }
        val tvosTest by getting {
            dependsOn(nativeTest)
        }
        if (ideaActive.not()) {
            val watchosMain by getting {
                dependsOn(nativeMain)
            }

            val watchosTest by getting {
                dependsOn(nativeTest)
            }
        }

        val watchosX86Main by getting {
            dependsOn(nativeMain)
        }

        val watchosX86Test by getting {
            dependsOn(nativeTest)
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }

        val mingwX64Test by getting {
            dependsOn(nativeTest)
        }

        if (ideaActive.not()) {
            val mingwX86Main by getting {
                dependsOn(nativeMain)
            }

            val mingwX86Test by getting {
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
