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
                implementation(project(":common:auth:presentation"))

                api(project(":common:main:driver"))
                api(project(":common:main:mechanic"))
                api(project(":common:main:observer"))

                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)
            }
        }
    }
}