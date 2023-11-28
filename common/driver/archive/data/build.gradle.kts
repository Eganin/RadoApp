plugins{
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies{
                implementation(project(":common:driver:archive:api"))
                implementation(project(":common:auth:data"))
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}