plugins {
    kotlin("jvm") version "1.9.21"
}
dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

group = "org.sofariu.aoc.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
    test {
        kotlin.srcDir("testSrc")
    }
}

sourceSets.main.configure {
    resources.srcDirs("src/resources").includes.addAll(arrayOf("**/*.*"))
}

sourceSets.test.configure {
    resources.srcDirs("testSrc/resources").includes.addAll(arrayOf("**/*.*"))
}


tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
