import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.liaimei"
version = "0.0.1"



val sinceVersion by extra("231.*")
val untilVersion by extra("241.*")
val jimmerVersion by extra("0.8.134")

sourceSets["main"].java.srcDirs("src/main/gen")

repositories {
    mavenCentral()
}

dependencies {
    testCompileOnly("org.babyfish.jimmer:jimmer-sql-kotlin:$jimmerVersion")
}
intellij {
    pluginName.set("SuperCodeEngine")
    version.set("2023.1")
    type.set("IU")
    downloadSources.set(true)
    plugins.set(
        listOf(
            "com.intellij.java",
            "org.jetbrains.kotlin",
            "com.intellij.spring",
            "com.intellij.database",
            "org.intellij.intelliLang",
            "com.intellij.spring.boot",
        )
    )
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjvm-default=all-compatibility")
        }
    }
    withType<JavaExec> {
        jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }


    test {
        systemProperty("idea.home.path", intellij.sandboxDir.get())
    }
    runIde {
        jvmArgs("-Xms128m", "-Xmx4096m", "-XX:ReservedCodeCacheSize=512m",)
    }

    patchPluginXml {
        sinceBuild.set(sinceVersion)
        untilBuild.set(untilVersion)
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
