object Dependencies {
    object Kotlin {
        private const val kotlinVersion = "1.9.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

        object Serialization {
            private const val version = "1.6.0"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinVersion"
        }

        object Coroutines {
            private const val version = "1.7.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        }
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
    }

    object Compose {
        private const val version = "1.5.1"
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$version"

        object AndroidXAppCompat{
            private const val version = "1.6.1"
            const val androidxAppCompat ="androidx.appcompat:appcompat:$version"
        }

        object Activity{
            private const val version = "1.7.2"
            const val activityCompose = "androidx.activity:activity-compose:$version"
        }

        object UiTooling{
            private const val version = "1.5.1"
            const val composeUiTooling ="androidx.compose.ui:ui-tooling:$version"
        }
    }

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.4.2"
    }

    object Resources{
        object Libres{
            private const val version = "1.1.8"
            const val libresCompose = "io.github.skeptick.libres:libres-compose"
            const val gradlePlugin = "io.github.skeptick.libres:gradle-plugin:$version"
        }

        object Image{
            private const val version = "1.6.7"
            const val composeImageLoader = "io.github.qdsfdhvh:image-loader"
        }

        object ComposeIcons{
            private const val version ="1.1.0"
            const val composeIconsFeatherIcons = "br.com.devsrsouza.compose.icons:feather"
        }
    }

    object Collections{

        object Immutable{
            private const val version = "0.3.5"
            const val core = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$version"
        }
    }

    object Persistence{
        object Settings {
            const val core = "com.russhwolf:multiplatform-settings:1.0.0"
            const val noargs = "com.russhwolf:multiplatform-settings-no-arg:1.0.0"
        }

        object SqlDelight {
            private const val version = "2.0.0"

            const val gradlePlugin = "app.cash.sqldelight:gradle-plugin:$version"

            const val sqliteDriver = "app.cash.sqldelight:sqlite-driver:$version"
            const val androidDriver = "app.cash.sqldelight:android-driver:$version"
            const val nativeDriver = "app.cash.sqldelight:native-driver:$version"
            const val jsDriver = "app.cash.sqldelight:web-worker-driver:$version"
        }
    }

    object Navigation{
        object ViewModel {
            private const val version = "0.14"
            const val core = "com.adeo:kviewmodel:$version"
            const val compose = "com.adeo:kviewmodel-compose:$version"
            const val odyssey = "com.adeo:kviewmodel-odyssey:$version"
        }

        object Navigation {
            private const val version = "1.3.20"
            const val core = "io.github.alexgladkov:odyssey-core:$version"
            const val compose = "io.github.alexgladkov:odyssey-compose:$version"
        }
    }

    object Other{
        object Napier{
            private const val  version = "2.6.1"
            const val napier = "io.github.aakira:napier"
        }
    }
}