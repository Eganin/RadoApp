object Dependencies {
    object Kotlin {
        private const val kotlinVersion = "1.9.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

        object Serialization {
            private const val version = "1.6.0"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"
        }

        object Coroutines {
            private const val version = "1.7.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object Kodein {
        const val core = "org.kodein.di:kodein-di:7.20.2"
    }

    object Ktor {
        private const val version = "2.3.4"
        const val core = "io.ktor:ktor-client-core:$version"
        const val json = "io.ktor:ktor-client-json:$version"
        const val ios = "io.ktor:ktor-client-ios:$version"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val kotlin_json = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val serialization = "io.ktor:ktor-client-serialization:$version"
        const val logging = "io.ktor:ktor-client-logging:$version"
        const val android = "io.ktor:ktor-client-android:$version"
        const val okhttp = "io.ktor:ktor-client-okhttp:$version"
        const val js = "io.ktor:ktor-client-js:$version"
    }

    object Compose {
        private const val version = "1.5.1"
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$version"

        object AndroidXAppCompat {
            private const val version = "1.6.1"
            const val androidxAppCompat = "androidx.appcompat:appcompat:$version"
        }

        object Activity {
            private const val version = "1.7.2"
            const val activityCompose = "androidx.activity:activity-compose:$version"
        }

        object UiTooling {
            private const val version = "1.5.1"
            const val composeUiTooling = "androidx.compose.ui:ui-tooling:$version"
        }
    }

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.4.2"

        object Compose {
            private const val version = "1.5.1"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val icons = "androidx.compose.material:material-icons-core:$version"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
        }
    }

    object Resources {
        object Libres {
            private const val version = "1.1.8"
            const val libresCompose = "io.github.skeptick.libres:libres-compose:$version"
            const val gradlePlugin = "io.github.skeptick.libres:gradle-plugin:$version"
        }

        object Image {
            private const val version = "1.6.7"
            const val composeImageLoader = "io.github.qdsfdhvh:image-loader:$version"
        }

        object ComposeIcons {
            private const val version = "1.1.0"
            const val composeIconsFeatherIcons = "br.com.devsrsouza.compose.icons:feather:$version"
        }
    }

    object Collections {

        object Immutable {
            private const val version = "0.3.5"
            const val core = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$version"
        }
    }

    object Persistence {
        object Settings {
            private const val version = "1.0.0"
            const val core = "com.russhwolf:multiplatform-settings:$version"
            const val noargs = "com.russhwolf:multiplatform-settings-no-arg:$version"
        }

        object SqlDelight {
            private const val version = "2.0.0"

            const val gradlePlugin = "app.cash.sqldelight:gradle-plugin:$version"

            //desktop
            const val sqliteDriver = "app.cash.sqldelight:sqlite-driver:$version"

            //android
            const val androidDriver = "app.cash.sqldelight:android-driver:$version"

            //ios
            const val nativeDriver = "app.cash.sqldelight:native-driver:$version"

            //web
            const val jsDriver = "app.cash.sqldelight:web-worker-driver:$version"
        }
    }

    object Navigation {
        object Voyager {
            private const val version = "1.0.0-rc07"
            const val navigator = "cafe.adriel.voyager:voyager-navigator:$version"
            const val kodeinIntegration = "cafe.adriel.voyager:voyager-kodein:$version"
            const val bottomSheetNavigator = "cafe.adriel.voyager:voyager-bottom-sheet-navigator:$version"
            const val tabNavigator = "cafe.adriel.voyager:voyager-tab-navigator:$version"
            const val transitions = "cafe.adriel.voyager:voyager-transitions:$version"
        }

        object MokoMVVM {
            private const val version = "0.16.1"
            const val core = "dev.icerock.moko:mvvm-compose:$version"
            const val flow = "dev.icerock.moko:mvvm-flow-compose:$version"
        }
    }

    object Other {
        object Napier {
            private const val version = "2.6.1"
            const val napier = "io.github.aakira:napier:$version"
        }

        object DateTime {
            private const val version = "0.4.1"
            const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
        }
    }
}