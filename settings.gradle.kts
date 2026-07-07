pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Napier"

include(":napier")
include(":mpp-sample")
include(":android")
include(":jvm")
include(":js")
