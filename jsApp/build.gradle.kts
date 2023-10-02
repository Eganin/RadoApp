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
            }
        }
    }
}

compose.experimental {
    web.application {}
}