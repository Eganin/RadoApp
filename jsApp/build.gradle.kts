plugins {
    id("multiplatform-setup")
    id("android-setup")
}

kotlin {

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        named("jsMain")  {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:umbrella-core"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}