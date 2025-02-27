import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "6.0.0"
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.23")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

group = "de.chojo"
version = "1.0"
description = "basicsqlplugin"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.named<ShadowJar>("shadowJar") {
    relocate("com.mysql", "de.chojo.simplecoins.mysql")
    relocate("com.google", "de.chojo.simplecoins.google")
    relocate("org.mariadb", "de.chojo.simplecoins.mariadb")
    // Dont use minimize. This will remove classes from your database driver which you will need.
    minimize {
        exclude(dependency("mysql:.*:.*"))
    }
    mergeServiceFiles()
}