plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("android")
}

android {
    namespace = "org.company.rado.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.company.rado.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":common:core"))
//    implementation(Dependencies.Compose.AndroidXAppCompat.androidxAppCompat)
    implementation(Dependencies.Compose.Activity.activityCompose)
//    implementation(Dependencies.Compose.UiTooling.composeUiTooling)
//    implementation("androidx.compose.ui:ui:1.5.1")
//    implementation("androidx.compose.ui:ui-tooling:1.4.3")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
//    implementation("androidx.compose.foundation:foundation:1.5.1")
//    implementation("androidx.compose.material:material:1.5.1")
//    implementation("androidx.activity:activity-compose:1.7.1")
}