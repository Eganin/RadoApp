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
include(":common:driver:archive:presentation")
include(":common:driver:archive:compose")

include(":common:driver:reject:api")
include(":common:driver:reject:data")
include(":common:driver:reject:presentation")
include(":common:driver:reject:compose")

include(":common:main:mechanic")

include(":common:mechanic:requests:api")
include(":common:mechanic:requests:data")
include(":common:mechanic:requests:presentation")
include(":common:mechanic:requests:compose")

include(":common:mechanic:archive:api")
include(":common:mechanic:archive:data")
include(":common:mechanic:archive:presentation")
include(":common:mechanic:archive:compose")

include(":common:mechanic:active:api")
include(":common:mechanic:active:data")
include(":common:mechanic:active:presentation")
include(":common:mechanic:active:compose")

include(":common:main:observer")

include(":common:observer:active:api")
include(":common:observer:active:data")
include(":common:observer:active:presentation")
include(":common:observer:active:compose")

include(":common:observer:archive:api")
include(":common:observer:archive:data")
include(":common:observer:archive:presentation")
include(":common:observer:archive:compose")

include(":common:observer:reject:api")
include(":common:observer:reject:data")
include(":common:observer:reject:presentation")
include(":common:observer:reject:compose")

include(":common:shared:requests:api")
include(":common:shared:requests:data")
include(":common:shared:requests:presentation")
include(":common:shared:requests:compose")
include(":common:shared:archive:presentation")
include(":common:shared:archive:compose")

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