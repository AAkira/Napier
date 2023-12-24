import dependencies.Dep
import dependencies.Versions
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply(from = rootProject.file("./gradle/publish.gradle.kts"))

kotlin {
    android {
        publishAllLibraryVariants()
    }
    js(IR) {
        browser()
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()
    jvm()

    // darwin
    if (ideaActive.not()) {
        // intel
        macosX64()
        ios()
        watchos()
        tvos()

        // apple silicon
        macosArm64()
        iosSimulatorArm64()
        watchosSimulatorArm64()
        tvosSimulatorArm64()
    } else {
        if (isAppleSilicon) {
            // apple silicon
            macosArm64()
            iosSimulatorArm64()
            watchosSimulatorArm64()
            tvosSimulatorArm64()
        } else {
            // intel
            macosX64()
            iosX64()
            watchosX64()
            tvosX64()
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
        val androidUnitTest by getting {
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

        // darwin
        if (ideaActive.not()) {
            // intel
            val macosX64Main by getting {
                dependsOn(darwinMain)
            }
            val macosX64Test by getting {
                dependsOn(darwinTest)
            }
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

            // apple silicon
            val macosArm64Main by getting {
                dependsOn(darwinMain)
            }
            val macosArm64Test by getting {
                dependsOn(darwinTest)
            }
            val iosSimulatorArm64Main by getting {
                dependsOn(darwinMain)
            }
            val iosSimulatorArm64Test by getting {
                dependsOn(darwinTest)
            }
            val watchosSimulatorArm64Main by getting {
                dependsOn(darwinMain)
            }
            val watchosSimulatorArm64Test by getting {
                dependsOn(darwinTest)
            }
            val tvosSimulatorArm64Main by getting {
                dependsOn(darwinMain)
            }
            val tvosSimulatorArm64Test by getting {
                dependsOn(darwinTest)
            }
        } else {
            if (isAppleSilicon) {
                // apple silicon
                val macosArm64Main by getting {
                    dependsOn(darwinMain)
                }
                val macosArm64Test by getting {
                    dependsOn(darwinTest)
                }
                val iosSimulatorArm64Main by getting {
                    dependsOn(darwinMain)
                }
                val iosSimulatorArm64Test by getting {
                    dependsOn(darwinTest)
                }
                val watchosSimulatorArm64Main by getting {
                    dependsOn(darwinMain)
                }
                val watchosSimulatorArm64Test by getting {
                    dependsOn(darwinTest)
                }
                val tvosSimulatorArm64Main by getting {
                    dependsOn(darwinMain)
                }
                val tvosSimulatorArm64Test by getting {
                    dependsOn(darwinTest)
                }
            } else {
                // intel
                val macosX64Main by getting {
                    dependsOn(darwinMain)
                }
                val macosX64Test by getting {
                    dependsOn(darwinTest)
                }
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
            }
        }
    }
}

android {
    compileSdk = Versions.compileSdkVersion
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        namespace = "io.github.aakira.napier"
        minSdk = Versions.minSdkVersion
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
