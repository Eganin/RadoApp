plugins{
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies{
                implementation(project(":common:driver:active:api"))
                implementation(project(":common:auth:data"))
                implementation(project(":common:core"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}