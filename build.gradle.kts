import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
}

group = "eli.solar"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.google.guava:guava:27.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.1")
    testImplementation("org.junit.platform:junit-platform-runner:1.4.1")
    testImplementation("org.junit.platform:junit-platform-engine:1.4.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}