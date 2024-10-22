plugins {
    id("com.android.library")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildFeatures{
        compose= true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
}