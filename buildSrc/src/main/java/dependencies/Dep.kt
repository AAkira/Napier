package dependencies

object Dep {

    private const val kotlin = "1.3.70"

    object GradlePlugin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dep.kotlin}"

        const val android = "com.android.tools.build:gradle:3.6.1"

        // There is a bug which is not included module files.
        const val bintray = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4-jetbrains-5"

        const val crashlytics = "io.fabric.tools:gradle:1.31.2"
        const val googleService = "com.google.gms:google-services:4.3.3"
    }

    object Kotlin {
        const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin"
    }

    object Coroutines {
        private const val version = "1.3.4"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$version"
        const val native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$version"
    }

    object Test {
        const val common = "org.jetbrains.kotlin:kotlin-test:$kotlin"
        const val annotation = "org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-test-junit:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-test-js:$kotlin"
    }

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
    }

    object Firebase {
        const val core = "com.google.firebase:firebase-core:17.2.3"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
    }
}
