/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    // version same as kotlin-gradle-plugin
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    checkstyle
    id("jacoco")
}

group = "com.github.shoothzj"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

configurations {
    all {
        // log4j1
        exclude("org.slf4j", "slf4j-log4j12")
        // logback
        exclude("ch.qos.logback", "logback-core")
        exclude("ch.qos.logback", "logback-classic")
    }
}

val jacocoVersion = "0.8.8"
val jschVersion = "0.2.1"
val jsonPathVersion = "2.7.0"
val kafkaVersion = "3.1.0"
val log4jVersion = "2.17.2"
val sdkVersion = "3.1.12"
val sshdVersion = "2.8.0"

dependencies {
    implementation(compose.desktop.currentOs)
    // middleware
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    // embedded middleware
    implementation("com.github.shoothzj:test-pulsar:$sdkVersion")
    implementation("com.github.shoothzj:test-kafka:$sdkVersion")
    implementation("com.github.shoothzj:test-zookeeper:$sdkVersion")
    // ssh
    implementation("com.github.mwiede:jsch:$jschVersion")
    // codec
    implementation("com.jayway.jsonpath:json-path:$jsonPathVersion")
    // base
    implementation("com.github.shoothzj:sdk-net:$sdkVersion")
    implementation("com.github.shoothzj:java-tool:$sdkVersion")
    // log
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("org.jacoco:org.jacoco.core:$jacocoVersion")
    // test
    testImplementation("org.apache.sshd:sshd-core:$sshdVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Deb, TargetFormat.Dmg, TargetFormat.Msi)
            modules("java.management")
            modules("java.naming")
            modules("java.security.jgss")
            packageName = "dev-tools-kotlin"
            packageVersion = "1.0.0"
        }
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

jacoco {
    toolVersion = "0.8.7"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "com/github/shoothzj/dev/test/**",
                        "constant/**",
                        "module/**",
                        "widget/**",
                        "R/**",
                        "**MainKt**"
                    )
                }
            }
        )
    )
    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("jacocoXml/report.xml"))
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
