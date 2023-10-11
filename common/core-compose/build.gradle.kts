plugins {
    id("multiplatform-setup")
    id("android-setup")
}


kotlin{
    sourceSets{
        commonMain{
            dependencies {
                implementation(Dependencies.Navigation.Voyager.tabNavigator)
            }
        }
    }
}