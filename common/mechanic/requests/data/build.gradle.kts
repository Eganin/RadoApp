plugins{
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies{
                implementation(project(":common:mechanic:requests:api"))
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:auth:data"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}