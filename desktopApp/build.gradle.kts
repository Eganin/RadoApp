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
                implementation(Dependencies.Navigation.Voyager.navigator)
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

            windows {
                menuGroup = "Rado"
                upgradeUuid = "9abd7638-ed9a-44ca-8680-98283941ef0a"
            }
        }
    }
}