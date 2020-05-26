import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.ivyxjc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11



repositories {
    mavenCentral()
}

configurations {
    all { exclude(group = "ch.qos.logback", module = "logback-core") }
    all { exclude(group = "ch.qos.logback", module = "logback-classic") }
    all { exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j") }
    all { exclude(group = "junit", module = "junit") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-artemis")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    runtimeOnly("mysql:mysql-connector-java:8.0.20")

    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.7")
    //log
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.25")
    runtimeOnly(group = "org.apache.logging.log4j", name = "log4j-slf4j-impl", version = "2.11.2")

    testImplementation(group = "org.junit.platform", name = "junit-platform-launcher", version = "1.6.2")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.6.2")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
