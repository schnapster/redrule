import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21" apply false
    application
    id("com.github.johnrengelman.shadow") version "4.0.4" apply false
}

project(":bot") {
    apply<KotlinPlatformJvmPlugin>()
    apply<ApplicationPlugin>()
    apply<ShadowPlugin>()

    project.group = "space.npstr.redrule"
    project.version = "0.0.1-SNAPSHOT"
    application.mainClassName = "space.npstr.redrule.bot.LauncherKt"

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    tasks.withType<ShadowJar> {
        archiveName = "redrule.jar"
    }

    repositories {
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://jitpack.io")
        }
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("com.mewna:catnip:0.13.2")
        implementation("ch.qos.logback:logback-classic:1.2.3")
    }
}
