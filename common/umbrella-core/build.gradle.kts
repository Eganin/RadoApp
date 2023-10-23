plugins{
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:auth:data"))
                implementation(project(":common:driver:active:data"))
                implementation(project(":common:shared:requests:data"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}