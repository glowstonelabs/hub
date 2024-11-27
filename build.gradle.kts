plugins {
    kotlin("jvm") version "2.1.0-RC2"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "wtf.amari"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
}

val versions = mapOf(
    "paperApi" to "1.21.1-R0.1-SNAPSHOT",
    "kotlinStdlib" to "2.1.0",
    "commando" to "b0ff9a152d",
    "mcChestUi" to "1.5.6",
    "placeholderApi" to "2.11.6",
    "kotlinCoroutines" to "1.9.0",
    "boostedyaml" to "1.3.7"
)

dependencies {
    compileOnly("io.papermc.paper:paper-api:${versions["paperApi"]}")
    compileOnly("me.clip:placeholderapi:${versions["placeholderApi"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions["kotlinStdlib"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions["kotlinCoroutines"]}")
    implementation("com.github.honkling.commando:spigot:${versions["commando"]}")
    implementation("com.github.DebitCardz:mc-chestui-plus:${versions["mcChestUi"]}")
    implementation("dev.dejvokep:boosted-yaml:${versions["boostedyaml"]}")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
    sourceSets["main"].kotlin.srcDirs("src/main/kotlin")
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = targetJavaVersion.toString()
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    register<Copy>("copyDependencies") {
        from(configurations.runtimeClasspath)
        into("$buildDir/libs")
    }

    processResources {
        inputs.properties(mapOf("version" to version))
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(mapOf("version" to version))
        }
    }

    shadowJar {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        exclude("**/unnecessary/**", "**/*.md", "**/README*")
    }
}