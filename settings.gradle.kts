rootProject.name = "Rado"
include(":jsApp")
include(":composeApp")
include(":androidApp")
include(":desktopApp")
include(":backend")

include(":common:auth:api")
include(":common:auth:data")
include(":common:auth:presentation")
include(":common:auth:compose")

include(":common:core")
include(":common:core-compose")
include(":common:core-utils")

include(":common:umbrella-ios")
include(":common:umbrella-compose")
include(":common:umbrella-core")

include(":common:main:driver")

include(":common:driver:active:api")
include(":common:driver:active:data")
include(":common:driver:active:presentation")
include(":common:driver:active:compose")

include(":common:driver:archive:api")
include(":common:driver:archive:data")

include(":common:main:mechanic")

include(":common:mechanic:requests:api")
include(":common:mechanic:requests:data")
include(":common:mechanic:requests:presentation")
include(":common:mechanic:requests:compose")

include(":common:mechanic:archive:api")
include(":common:mechanic:archive:data")

include(":common:mechanic:active:api")
include(":common:mechanic:active:data")

include(":common:main:observer")

include(":common:observer:active:api")
include(":common:observer:active:data")

include(":common:observer:archive:api")
include(":common:observer:archive:data")

include(":common:shared:reject:api")
include(":common:shared:reject:data")
include(":common:shared:reject:presentation")
include(":common:shared:reject:compose")

include(":common:shared:requests:api")
include(":common:shared:requests:data")
include(":common:shared:requests:presentation")
include(":common:shared:requests:compose")
include(":common:shared:archive:presentation")
include(":common:shared:archive:compose")
include(":common:shared:active:presentation")
include(":common:shared:active:compose")

include(":common:permission:camera")
include(":common:permission:phone")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}