plugins{
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("multiplatform")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin{
    targetHierarchy.default()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    js(IR) {
        browser()
    }

    ios()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.foundation)
                implementation(Dependencies.Other.Napier.napier)
                implementation(Dependencies.Kotlin.Coroutines.core)
                implementation(Dependencies.Other.DateTime.kotlinxDatetime)
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        named("jsMain") {
            dependencies {
                implementation(compose.html.core)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(Dependencies.Android.Compose.ui)
                implementation(Dependencies.Android.Compose.material)
                implementation(Dependencies.Android.Compose.tooling)
                implementation(Dependencies.Kotlin.Coroutines.android)
            }
        }
    }
}