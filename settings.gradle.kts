pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.51.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions")
}


refreshVersions { // Optional: configure the plugin
    // ...
}

rootProject.name = "getenv-kt"

include(":getenv")