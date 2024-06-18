plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "2.0.0"
    id("idea")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.0.1"
    id("xyz.jpenilla.run-velocity") version "2.3.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    compileOnly("io.github.dreamvoid:MiraiMC-Velocity:1.8")
    implementation("com.charleskorn.kaml:kaml:0.60.0")
    implementation("net.kyori:adventure-api:4.17.0")
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
}

group = "io.yukonisen"
version = "0.2"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

tasks.withType<ProcessResources> {
    filesMatching("velocity-plugin.json") {
        expand(mapOf("version" to project.version))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks {
    runVelocity {
        velocityVersion("3.3.0-SNAPSHOT")
    }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition", "-XX:+AllowRedefinitionToAddDeleteMethods")
}