plugins {
    id("multiplatform-setup")
    id("android-setup")
}


kotlin{
    sourceSets{
        commonMain{
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:permission:camera"))
                implementation(Dependencies.Navigation.Voyager.tabNavigator)
                implementation(Dependencies.Resources.ComposeIcons.composeIconsFeatherIcons)
            }
        }
    }
}