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
                implementation(project(":common:mechanic:requests:data"))
                implementation(project(":common:mechanic:active:data"))
                implementation(project(":common:mechanic:archive:data"))
                implementation(project(":common:driver:archive:data"))
                implementation(project(":common:observer:archive:data"))
                implementation(project(":common:observer:active:data"))

                implementation(Dependencies.Kodein.core)
            }
        }
    }
}