import dependencies.Dep

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":napier"))
    implementation(project(":mpp-sample"))

    implementation(Dep.Kotlin.jvm)
    implementation(Dep.Coroutines.core)
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "io.github.aakira.napier.sample.MainKt"
    }

    from(
        configurations["runtimeClasspath"].map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
    exclude("META-INF/versions/9/module-info.class")
}
