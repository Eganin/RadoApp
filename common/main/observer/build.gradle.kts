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
                implementation(project(":common:core"))
                implementation(project(":common:shared:archive:compose"))
                implementation(project(":common:shared:active:compose"))
                implementation(project(":common:shared:reject:compose"))
                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.Voyager.tabNavigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)
                implementation(Dependencies.Resources.ComposeIcons.composeIconsFeatherIcons)
            }
        }
    }
}