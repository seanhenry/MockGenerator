val idePath = "/Users/seanhenry/Library/Application Support/JetBrains/Toolbox/apps/AppCode/ch-0/221.5921.25/AppCode.app/Contents"

plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.6.20"
  id("org.jetbrains.intellij") version "1.5.2"
}

group = "codes.seanhenry.mockgenerator"
version = "19"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.github.spullara.mustache.java:compiler:0.9.10")
  testImplementation(kotlin("test"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

intellij {
  localPath.set(idePath)
  // look in AppCode.app/Contents/Plugins/builtinRegistry.xml to find bundled plugins
  plugins.set(listOf("com.intellij.swift.lang", "com.intellij.cidr.base"))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }

  runIde {
    ideDir.set(File(idePath))
    jvmArgs("-Xmx4096m")
  }

  patchPluginXml {
    changeNotes.set("Adds support for AppCode 2022.1")
    sinceBuild.set("221")
  }

  signPlugin {
    certificateChain.set(File("./signing/chain.crt").readText())
    privateKey.set(File("./signing/private.pem").readText())
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }

  test {
    useJUnitPlatform()
  }
}
