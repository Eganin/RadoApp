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
                implementation(project(":common:shared:requests:presentation"))
                implementation(project(":common:permission:camera"))

                implementation(Dependencies.Navigation.Voyager.navigator)
                implementation(Dependencies.Navigation.MokoMVVM.core)

                implementation(Dependencies.Resources.Libres.libresCompose)
                implementation(Dependencies.Resources.Image.composeImageLoader)
                implementation(Dependencies.Resources.ComposeIcons.composeIconsFeatherIcons)

                implementation(Dependencies.Other.FilePicker.core)

                implementation(Dependencies.Other.UUID.core)
            }
        }

        desktopMain{
            dependencies {
                implementation(Dependencies.Desktop.VLC.core)
            }
        }

        androidMain{
            dependencies {
                implementation(Dependencies.Android.ExoPlayer.core)
                implementation(Dependencies.Android.ExoPlayer.ui)
                implementation(Dependencies.Android.ExoPlayer.dash)
            }
        }
    }
}