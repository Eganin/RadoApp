plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    gradlePluginPortal()
    mavenLocal()
    google()
}

dependencies {
    implementation(Dependencies.Kotlin.gradlePlugin)
    implementation(Dependencies.Compose.gradlePlugin)
    implementation(Dependencies.Android.gradlePlugin)
    implementation(Dependencies.Kotlin.Serialization.gradlePlugin)
    implementation(Dependencies.Resources.Libres.gradlePlugin)
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}