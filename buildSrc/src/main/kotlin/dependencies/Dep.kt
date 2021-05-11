package dependencies

object Dep {

    private const val kotlin = "1.5.0"

    object GradlePlugin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dep.kotlin}"

        const val android = "com.android.tools.build:gradle:4.1.3"

        const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.5.2"
        const val googleService = "com.google.gms:google-services:4.3.5"
    }

    object Kotlin {
        const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin"
    }

    object Coroutines {
        private const val version = "1.5.0-RC"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    object Test {
        const val common = "org.jetbrains.kotlin:kotlin-test:$kotlin"
        const val annotation = "org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-test-junit:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-test-js:$kotlin"
    }

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    }

    object Firebase {
        const val platform = "com.google.firebase:firebase-bom:27.1.0"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    }
}
