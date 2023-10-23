plugins{
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies{
                implementation(project(":common:shared:requests:api"))
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}