plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("native.cocoapods")
}

version = "0.0.3"

kotlin {
    jvmToolchain(11)
    cocoapods {
        summary = "Rado iOS SDK"
        homepage = "https://google.com"
        ios.deploymentTarget = "14.0"

        framework {
            transitiveExport = false
            baseName = "SharedSDK"
            export(project(":common:core"))
            export(project(":common:umbrella-compose"))
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:umbrella-compose"))
                implementation(project(":common:permission:camera"))
            }
        }

        iosMain {
            dependencies {
                api(project(":common:core"))
                api(project(":common:umbrella-compose"))
                api(project(":common:permission:camera"))
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}