plugins {
    id("java")
    id("house.greenhouse.evergreen")
}

repositories {
}

dependencies {
}

evergreen {
    minecraftVersion = "1.21.1"
    xplat {}
    fabric {
        loaderVersion = "0.16.14"
    }
}