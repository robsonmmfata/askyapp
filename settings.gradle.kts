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
        maven {
            url = uri("https://packagecloud.io/priv/3c344cb8139ec2e428827b20f247ae4fcf392b1035ac64c1/stone/pos-android/maven2")
        }
        maven {
            url = uri("https://github.com/pagseguro/PlugPagServiceWrapper/raw/master")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://packagecloud.io/priv/3c344cb8139ec2e428827b20f247ae4fcf392b1035ac64c1/stone/pos-android/maven2")
        }
        maven {
            url = uri("https://github.com/pagseguro/PlugPagServiceWrapper/raw/master")
        }
    }
}

rootProject.name = "Asky"
include(":app")
