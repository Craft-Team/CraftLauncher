pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Craft Launcher"
include(":CraftLauncher")
include(":LWJGL-BOAT")
include(":LWJGL-BOAT")
include(":LWJGL-POJAV")
include(":LWJGL-CRAFT")
include(":CraftCore")
