plugins {
    id("module-config")
    id("com.adarshr.test-logger") version "4.0.0"
}

version = "unspecified"
extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-amqp") // RabbitMQ
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA
    implementation("org.springframework.boot:spring-boot-starter-jdbc") // JDBC
    implementation("org.springframework.boot:spring-boot-starter-web") // Web MVC
    implementation("org.springframework.boot:spring-boot-starter-webflux") // WebFlux
    implementation("org.springframework.boot:spring-boot-starter-security") // Security
    implementation("org.springframework.boot:spring-boot-starter-websocket") // WebSocket
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server") // OAuth2 Resource Server

    runtimeOnly("com.h2database:h2") // H2 Database for tests

    // Test Dependencies
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") // Includes JUnit 5
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:rabbitmq")

    // JUnit BOM for consistent versions
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

subprojects {
    apply {
        plugin("com.adarshr.test-logger")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

// Unit Tests
val unitTest by tasks.registering(Test::class) {
    description = "Runs unit tests."
    group = "verification"
    useJUnitPlatform()
    include("**/unit/**") // Filters tests located in the 'unit' package
}

// Integration Tests
val integrationTest by tasks.registering(Test::class) {
    description = "Runs integration tests."
    group = "verification"
    useJUnitPlatform()
    include("**/integration/**") // Filters tests located in the 'integration' package
}

// Add unitTest and integrationTest to the check lifecycle
tasks.named("check") {
    dependsOn(unitTest, integrationTest)
}