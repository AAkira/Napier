import dependencies.Dep
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":napier"))
    implementation(project(":mpp-sample"))

    implementation(Dep.Kotlin.js)
    implementation(Dep.Coroutines.core)
}

kotlin {
    js {
        browser {
            // execute :js:browserRun to launch dev server
            runTask {
                devServer = KotlinWebpackConfig.DevServer(
                    open = true,
                    port = 8080,
                    proxy = null,
                    contentBase = mutableListOf("${projectDir}/src/main/resources")
                )
                outputFileName = "main.js"
            }
            // execute :js:browserWebpack to build webpack bundle in `./build/distributions`
            webpackTask {
                outputFileName = "main.js"
            }
        }
    }
}
