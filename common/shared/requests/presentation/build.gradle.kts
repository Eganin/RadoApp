plugins{
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies {
                api(project(":common:driver:active:api"))
                api(project(":common:shared:requests:api"))
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:permission:camera"))

                implementation(Dependencies.Navigation.MokoMVVM.core)
                implementation(Dependencies.Navigation.MokoMVVM.flow)
            }
        }
    }
}