import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions { jvmTarget = "16" }

val outputName = "EconomyAPI"
val outputDir = "OUTPUT"

group = "com.github.xIrinaShaykx"
version = "1.0.0-SNAPSHOT"



plugins {
    maven
    java
    kotlin("jvm") version "1.5.21"
}

tasks {
    withType<JavaCompile> {
        options.fork(mapOf(Pair("jvmArgs", listOf("--add-opens", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED"))))
    }
}

tasks.jar {
    //Output name
    duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
    archiveFileName.set("$outputName.jar")
    destinationDirectory.set(file("../OUTPUT"))
    //Shade dependecies


    configurations.compileClasspath.get().filter {
        it.name.startsWith("SpigotLibrary") || it.name.startsWith("KotlinLibrary") || it.name.startsWith("kotlin") || it.name.startsWith("h2")
    }.forEach { from(zipTree(it.absolutePath)) }
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    mavenCentral()
    maven("https://repo.kinqdos.de/artifactory/kinqdos-repo")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.kinqdos", "spigot", "1.17")
    implementation("com.kinqdos", "SpigotLibrary", "1.16.4-V1.0.3")
    implementation("com.github.jitpack", "gradle-simple", "1.1")
    implementation(kotlin("stdlib-jdk8"))
}