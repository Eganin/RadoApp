plugins{
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies {
                implementation(project(":common:core-compose"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:driver:active:presentation"))

                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)
            }
        }
    }
}