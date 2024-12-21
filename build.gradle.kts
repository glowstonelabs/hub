import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktlint by configurations.creating

plugins {
    kotlin("jvm") version "2.1.0"
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
    maven("https://repo.codemc.io/repository/maven-releases/")
}

val versions =
    mapOf(
        "paperApi" to "1.21.3-R0.1-SNAPSHOT",
        "kotlinStdlib" to "2.1.0",
        "commando" to "b0ff9a152d",
        "mcChestUi" to "1.5.6",
        "placeholderApi" to "2.11.6",
        "kotlinCoroutines" to "1.9.0",
        "boostedyaml" to "1.3.7",
        "ktlint" to "1.5.0",
    )

dependencies {
    compileOnly("io.papermc.paper:paper-api:${versions["paperApi"]}")
    compileOnly("me.clip:placeholderapi:${versions["placeholderApi"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions["kotlinStdlib"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions["kotlinCoroutines"]}")
    implementation("com.github.honkling.commando:spigot:${versions["commando"]}")
    implementation("com.github.DebitCardz:mc-chestui-plus:${versions["mcChestUi"]}")
    implementation("dev.dejvokep:boosted-yaml:${versions["boostedyaml"]}")
    ktlint("com.pinterest.ktlint:ktlint-cli:${versions["ktlint"]}") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style"
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args("**/src/**/*.kt", "**.kts", "!**/build/**")
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
    check {
        dependsOn(ktlintCheck)
    }
    build {
        dependsOn("shadowJar")
    }
    register<Copy>("copyDependencies") {
        from(configurations.runtimeClasspath)
        into("$buildDir/libs")
    }
    register<JavaExec>("ktlintFormat") {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Check Kotlin code style and format"
        classpath = ktlint
        mainClass.set("com.pinterest.ktlint.Main")
        jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
        args("-F", "**/src/**/*.kt", "**.kts", "!**/build/**")
    }

    processResources {
        inputs.properties(mapOf("version" to version))
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(mapOf("version" to version))
        }
    }
    withType<ShadowJar> {
        relocate("com.github.honkling.commando", "wtf.amari.hub.libs.commando")
        relocate("com.github.DebitCardz.mcchestui", "wtf.amari.hub.libs.chestui")
        relocate("dev.dejvokep.boostedyaml", "wtf.amari.hub.libs.yaml")
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        exclude("**/unnecessary/**", "**/*.md", "**/README*")
        minimize()
        archiveClassifier.set("")
    }
}
