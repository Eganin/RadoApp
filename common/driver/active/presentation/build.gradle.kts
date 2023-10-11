plugins{
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies {
                api(project(":common:driver:active:api"))
                api(project(":common:core"))

                implementation(Dependencies.Navigation.MokoMVVM.core)
                implementation(Dependencies.Navigation.MokoMVVM.flow)
            }
        }
    }
}