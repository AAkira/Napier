import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "main.js"

                // for :js:jsBrowserRun dev server
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    open = true,
                    port = 8080,
                    static = mutableListOf("${projectDir}/src/jsMain/resources"),
                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(project(":napier"))
            implementation(project(":mpp-sample"))

            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
