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
                implementation(project(":common:driver:active:presentation"))
                implementation(project(":common:shared:active:compose"))

                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)

                implementation(Dependencies.Resources.Libres.libresCompose)
                implementation(Dependencies.Resources.Image.composeImageLoader)
                implementation(Dependencies.Resources.ComposeIcons.composeIconsFeatherIcons)
            }
        }

        androidMain{
            dependencies{
                implementation(Dependencies.Other.FilePicker.core)
            }
        }

        desktopMain{
            dependencies{
                implementation(Dependencies.Other.FilePicker.core)
            }
        }

        jsMain{
            dependencies{
                implementation(Dependencies.Other.FilePicker.core)
            }
        }
    }
}