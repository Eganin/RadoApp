plugins{
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-compose"))
                implementation(project(":common:core-utils"))
                implementation(project(":common:shared:reject:presentation"))
                implementation(project(":common:shared:requests:compose"))
                implementation(project(":common:shared:requests:presentation"))

                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)

                implementation(Dependencies.Resources.Libres.libresCompose)
                implementation(Dependencies.Resources.Image.composeImageLoader)
                implementation(Dependencies.Resources.ComposeIcons.composeIconsFeatherIcons)

                implementation(Dependencies.Other.FilePicker.core)
            }
        }
    }
}