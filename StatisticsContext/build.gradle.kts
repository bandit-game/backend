plugins {
    id("module-config")
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudAzureVersion"] = "5.18.0"

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.azure.spring:spring-cloud-azure-starter")
    implementation("com.azure.spring:spring-cloud-azure-starter-jdbc-postgresql")
    implementation("com.azure.spring:spring-cloud-azure-starter-storage")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    testImplementation("org.springframework.boot:spring-boot-starter-test") // Includes JUnit 5
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:rabbitmq")
    testImplementation("org.testcontainers:postgresql")

}

dependencyManagement {
    imports {
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
