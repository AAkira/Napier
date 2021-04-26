import dependencies.Versions

apply(plugin = "maven-publish")
apply(plugin = "signing")

// read values from gradle.properties
val mavenGroup: String by project
val projectName: String by project
val pomDescription: String by project
val siteUrl: String by project
val pomLicenseName: String by project
val pomLicenseUrl: String by project
val pomLicenseDist: String by project
val pomDeveloperId: String by project
val pomDeveloperName: String by project
val pomOrganizationName: String by project
val pomOrganizationUrl: String by project

configure<PublishingExtension> {
    publications.all {
        group = mavenGroup
        version = Versions.versionName
    }

    publications.withType<MavenPublication> {
        pom {
            name.set(projectName)
            description.set(pomDescription)
            url.set(siteUrl)
            licenses {
                license {
                    name.set(pomLicenseName)
                    url.set(pomLicenseUrl)
                    distribution.set(pomLicenseDist)
                }
            }
            developers {
                developer {
                    id.set(pomDeveloperId)
                    name.set(pomDeveloperName)
                    organization.set(pomOrganizationName)
                    organizationUrl.set(pomOrganizationUrl)
                }
            }
            scm {
                url.set(siteUrl)
            }
        }
    }

    repositories {
        maven {
            name = "napier"
            url = uri(
                if (version.toString().endsWith("SNAPSHOT")) {
                    "https://oss.sonatype.org/content/repositories/snapshots"
                } else {
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2"
                }
            )

            credentials {
                username = project.properties["sonatypeUser"] as String
                password = project.properties["sonatypePassword"] as String
            }
        }
    }

    configure<SigningExtension> {
        sign(publications)
    }
}
