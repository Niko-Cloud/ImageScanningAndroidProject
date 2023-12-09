pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven {         // add this repo to use snapshots
            name = "ossrh-snapshot"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CapstoneProject"
include(":app")
