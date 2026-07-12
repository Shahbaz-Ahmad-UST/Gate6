import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

plugins {
    java
}

group = "com.ust.shopkart"
version = "1.0.0"

repositories {
    mavenCentral()
}

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.14.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "1.21.3"
val flywayVersion = "10.22.0"
val mysqlVersion = "9.4.0"
val restAssuredVersion = "5.5.6"
val jacksonVersion = "2.20.0"


java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

dependencies {

    // BOM
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    // Selenium 

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")

    //   JUnit  

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")

    //   Cucumber  

    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")

    //   REST Assured  

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:${restAssuredVersion}")
    testImplementation("io.rest-assured:xml-path:${restAssuredVersion}")
    testImplementation("io.rest-assured:json-schema-validator:${restAssuredVersion}")

    //   Jackson  

    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    //   Allure  

    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit5")

    //   Extent Reports  

    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")

    //   Logging  

    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    //   TestContainers  

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")

    //   Flyway  

    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-mysql:$flywayVersion")

    //   MySQL  

    testImplementation("com.mysql:mysql-connector-j:$mysqlVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
    options.compilerArgs.add("-parameters")
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.withType<Test>().configureEach {

    useJUnitPlatform()

    maxParallelForks = 1

    systemProperty(
        "baseUrl",
        System.getProperty("baseUrl", "http://localhost:5173")
    )

    systemProperty(
        "headless",
        System.getProperty("headless", "false")
    )

    systemProperty(
        "build.label",
        System.getProperty("build.label", "local")
    )

    systemProperty(
        "allure.results.directory",
        System.getProperty(
            "allure.results.directory",
            "build/allure-results"
        )
    )

    testLogging {
        events("passed", "failed", "skipped")
    }
}

tasks.test {
    description = "Runs all tests."
    group = "verification"
}

val OrderTest by tasks.registering(Test::class) {
    description = "Runs DummyOrderTest (self-contained, DB-only, no backend/frontend needed)"
    group = "verification"

    useProjectTestClasses()

    include("**/OrderTest.class")
    maxParallelForks = 1
}

val CancelOrderApiTest by tasks.registering(Test::class) {
    description = "Runs CancelOrderApiTest "
    group = "verification"

    useProjectTestClasses()

    include("**/CancelOrderApiTest.class")
    maxParallelForks = 1
}

val CheckoutE2ETest by tasks.registering(Test::class) {
    description = "Runs CheckoutE2ETest "
    group = "verification"

    useProjectTestClasses()

    include("**/CheckoutE2ETest.class")
    maxParallelForks = 1
}
val OrderAccessApiTest by tasks.registering(Test::class) {
    description = "Runs OrderAccessApiTest "
    group = "verification"

    useProjectTestClasses()

    include("**/OrderAccessApiTest.class")
    maxParallelForks = 1
}

val OutOfStockApiTest by tasks.registering(Test::class) {
    description = "Runs OutOfStockApiTest "
    group = "verification"

    useProjectTestClasses()

    include("**/OutOfStockApiTest.class")
    maxParallelForks = 1
}
val ProdutApiTest by tasks.registering(Test::class) {
    description = "Runs ProdutApiTest "
    group = "verification"

    useProjectTestClasses()

    include("**/ProdutApiTest.class")
    maxParallelForks = 1
}

val TotalCartApiTest by tasks.registering(Test::class) {
    description = "Runs TotalCartApiTest "
    group = "verification"

    useProjectTestClasses()

    include("**/TotalCartApiTest.class")
    maxParallelForks = 1
}

val RunCucumberTest by tasks.registering(Test::class) {
    description = "Runs RunCucumberTest "
    group = "verification"

    useProjectTestClasses()

    include("**/RunCucumberTest.class")
    maxParallelForks = 1
}


val applicationSuite  by tasks.registering(Test::class) {
    description = "Runs API, E2E and Cucumber tests"
    group = "verification"

    useProjectTestClasses()

    include("**/CancelOrderApiTest.class")
    include("**/CheckoutE2ETest.class")
    include("**/OrderAccessApiTest.class")
    include("**/OutOfStockApiTest.class")
    include("**/ProdutApiTest.class")
    include("**/TotalCartApiTest.class")
    include("**/RunCucumberTest.class")

    maxParallelForks = 1
    ignoreFailures = true
}

val repositorySuite by tasks.registering(Test::class) {
    description = "Repository database tests"
    group = "verification"

    useProjectTestClasses()

    include("**/OrderTest.class")
}