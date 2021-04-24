import dependencies.Versions

apply(plugin = "maven-publish")
apply(plugin = "signing")

val BINTRAY_PACKAGE: String by project
val POM_DESCRIPTION: String by project
val SITE_URL: String by project
val POM_LICENSE_NAME: String by project
val POM_LICENSE_URL: String by project
val POM_LICENSE_DIST: String by project
val POM_DEVELOPER_ID: String by project
val POM_DEVELOPER_NAME: String by project
val POM_ORGANIZATION_NAME: String by project
val POM_ORGANIZATION_URL: String by project

configure<PublishingExtension> {
//publishing {
    publications.all {
        group = BINTRAY_PACKAGE
        version = Versions.versionName
    }

    publications.withType<MavenPublication> {
        pom {

            name.set("napier")
            description.set(POM_DESCRIPTION)
            url.set(SITE_URL)
            licenses {
                license {
                    name.set(POM_LICENSE_NAME)
                    url.set(POM_LICENSE_URL)
                    distribution.set(POM_LICENSE_DIST)
                }
            }
            developers {
                developer {
                    id.set(POM_DEVELOPER_ID)
                    name.set(POM_DEVELOPER_NAME)
                    organization.set(POM_ORGANIZATION_NAME)
                    organizationUrl.set(POM_ORGANIZATION_URL)
                }
            }
            scm {
                url.set(SITE_URL)
            }
        }
    }

    repositories {
        maven {
//            user = 'aakira'
//            repo = 'maven'
            name = "napier"
            url = uri(
                if (version.toString().endsWith("SNAPSHOT")) {
                    "https://oss.sonatype.org/content/repositories/snapshots"
                } else {
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2"
                }
            )

            credentials {
                username = bintrayUserProperty
                password = bintrayApiKeyProperty
            }
        }
    }
}

val bintrayUserProperty: String? =
    if (hasProperty("bintrayUser")) project.property("bintrayUser") as String else System.getenv("BINTRAY_USER")

val bintrayApiKeyProperty: String? =
    if (hasProperty("bintrayApiKey")) project.property("bintrayApiKey") as String else System.getenv(
        "BINTRAY_API_KEY"
    )
