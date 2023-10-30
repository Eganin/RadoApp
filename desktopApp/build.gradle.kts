import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:umbrella-compose"))
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )
            packageName = "org.company.rado.desktopApp"
            packageVersion = "1.0.0"

            linux {
                // a version for all Linux distributables
                packageVersion = "1.0.0"
                // a version only for the deb package
                debPackageVersion = "1.0.0"
                // a version only for the rpm package
                rpmPackageVersion = "1.0.0"
            }

            windows {
                menuGroup = "Rado"
                upgradeUuid = "9abd7638-ed9a-44ca-8680-98283941ef0a"
                packageVersion = "1.0.0"
                msiPackageVersion="1.0.0"
                exePackageVersion="1.0.0"
            }
            macOS {
                // a version for all macOS distributables
                packageVersion = "1.0.0"
                // a version only for the dmg package
                dmgPackageVersion = "1.0.0"
                // a version only for the pkg package
                pkgPackageVersion = "1.0.0"

                // a build version for all macOS distributables
                packageBuildVersion = "1.0.0"
                // a build version only for the dmg package
                dmgPackageBuildVersion = "1.0.0"
                // a build version only for the pkg package
                pkgPackageBuildVersion = "1.0.0"
            }
        }
    }
}