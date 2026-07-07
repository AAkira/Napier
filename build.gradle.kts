plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false

    // for crashlytics sample
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
}
