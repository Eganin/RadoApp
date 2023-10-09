plugins {
    id("multiplatform-setup")
    id("android-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                api(project(":common:core-compose"))
                api(project(":common:umbrella-core"))
                implementation(project(":common:core-utils"))

                implementation(project(":common:auth:compose"))

                implementation(Dependencies.Navigation.MokoMVVM.core)
                implementation(Dependencies.Navigation.MokoMVVM.flow)

                implementation(Dependencies.Navigation.Voyager.navigator)
            }
        }

        androidMain{
            dependencies{
                implementation(Dependencies.Compose.Activity.activityCompose)
            }
        }
    }
}