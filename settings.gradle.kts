pluginManagement {
    repositories {
        google()
//        maven ( url = "https://kotlin.bintray.com/kotlinx")
        mavenCentral()
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

rootProject.name = "Assessment"
include(":app")
include(":core:network")
include(":features:auth")
include(":core:theme")
include(":core:resources")
include(":core:domain")
include(":core:data")
include(":core:utils")
include(":features:dashboard")
include(":features:productDetails")
