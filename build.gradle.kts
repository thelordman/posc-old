plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.4"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.lord"
version = "1.0-INDEV"
description = "Plugin for the Posc Minecraft server"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    implementation("net.dv8tion", "JDA", "5.0.0-beta.3") {
        exclude("club.minnced", "opus-java")
    }
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}