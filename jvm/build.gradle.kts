plugins {
    alias(libs.plugins.kotlinJvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":napier"))
    implementation(project(":mpp-sample"))

    implementation(libs.kotlinx.coroutines.core)
}

val jar by tasks.getting(Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

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
