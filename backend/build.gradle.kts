val ktor_version: String = "2.3.4"
val kotlin_version: String = "1.9.10"
val logback_version: String = "1.4.11"
val exposed_version: String = "0.41.1"
val gson_version: String = "2.9.0"

repositories {
    mavenCentral()
}

plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin") version "2.3.4"
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.company.rado"
version = "0.0.13"

application {
    mainClass.set("org.company.rado.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.code.gson:gson:$gson_version")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:7.19.0")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testImplementation("io.ktor:ktor-client-content-negotiation:$kotlin_version")

    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("com.koriit.kotlin:ktor-logging:0.4.0")
    implementation("io.ktor:ktor-server-compression:$ktor_version")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation("io.ktor:ktor-server-partial-content:$ktor_version")
}

ktor {
    fatJar {
        archiveFileName.set("ru.rado-restapi-$version-all.jar")
    }
}