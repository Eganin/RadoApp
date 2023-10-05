plugins {
    id("multiplatform-setup")
    id("android-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:core-compose"))
                api(project(":common:umbrella-core"))
                implementation(project(":common:core-utils"))


                implementation(Dependencies.Navigation.ViewModel.core)
                implementation(Dependencies.Navigation.ViewModel.compose)
                implementation(Dependencies.Navigation.ViewModel.odyssey)

                implementation(Dependencies.Navigation.Odyssey.compose)
                implementation(Dependencies.Navigation.Odyssey.core)
            }
        }

        androidMain{
            dependencies{
                implementation(Dependencies.Compose.Activity.activityCompose)
            }
        }
    }
}