plugins {
    id("multiplatform-setup")
    id("android-setup")
    kotlin("plugin.serialization")
    //id("app.cash.sqldelight") version "2.0.0"
    id("io.github.skeptick.libres")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(Dependencies.Kotlin.Serialization.serialization)
                api(Dependencies.Kotlin.Coroutines.core)

                api(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.json)
                implementation(Dependencies.Ktor.kotlin_json)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.Ktor.logging)
                implementation(Dependencies.Ktor.negotiation)

                implementation(Dependencies.Persistence.Settings.core)
                implementation(Dependencies.Persistence.Settings.noargs)

                implementation(Dependencies.Navigation.MokoMVVM.core)
                implementation(Dependencies.Navigation.MokoMVVM.flow)

                api(Dependencies.Kodein.core)
            }
        }

        androidMain {
            dependencies {
                implementation(Dependencies.Ktor.android)
                //implementation(Dependencies.Persistence.SqlDelight.androidDriver)
                implementation(Dependencies.Resources.Libres.libresCompose)
            }
        }

        iosMain {
            dependencies {
                implementation(Dependencies.Ktor.ios)
                //implementation(Dependencies.Persistence.SqlDelight.nativeDriver)
            }
        }

        desktopMain {
            dependencies {
                implementation(Dependencies.Ktor.okhttp)
                //implementation(Dependencies.Persistence.SqlDelight.sqliteDriver)
            }
        }

        jsMain{
            dependencies {
                implementation(Dependencies.Ktor.js)
                //implementation(Dependencies.Persistence.SqlDelight.jsDriver)
                implementation(npm("sql.js", "1.6.2"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}

//sqldelight {
//    databases {
//        create("Database") {
//            packageName.set("org.company.rado")
//            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases/schema"))
//            migrationOutputDirectory.set(file("src/commonMain/sqldelight/databases/migrations"))
//        }
//    }
//}

libres {
    generatedClassName = "MainRes" // "Res" by default
    generateNamedArguments = true // false by default
    baseLocaleLanguageCode = "ru" // "en" by default
    camelCaseNamesForAppleFramework = false // false by default
}