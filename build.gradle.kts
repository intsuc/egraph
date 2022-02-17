plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("it.unimi.dsi:fastutil:8.5.8")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
