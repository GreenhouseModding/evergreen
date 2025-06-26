plugins {
    id("java")
    id("maven-publish")
    id("idea")
    `kotlin-dsl`
}

base.archivesName = "evergreen"
group = "house.greenhouse"
version = properties["version"]!!

// Maintain Java 17 compatibility for old versions of Fabric/NeoForge.
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    maven("https://maven.fabricmc.net/") {
        name = "Fabric"
        mavenContent {
            includeGroup("net.fabricmc")
        }
    }
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.jetbrains.annotations)
    implementation(libs.apache.commons)

    implementation(libs.fabric.mapping.io)
    implementation(libs.fabric.tiny.remapper)
}

gradlePlugin {
    plugins {
        create("evergreen") {
            id = "house.greenhouse.evergreen"
            implementationClass = "house.greenhouse.evergreen.EvergreenPlugin"
        }
    }
}