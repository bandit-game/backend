plugins {
    id("module-config")
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudAzureVersion"] = "5.18.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.azure.spring:spring-cloud-azure-starter")
    implementation("com.azure.spring:spring-cloud-azure-starter-jdbc-postgresql")
    implementation("com.azure.spring:spring-cloud-azure-starter-storage")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("com.azure.spring:spring-cloud-azure-testcontainers")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:rabbitmq")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
    }
}
//
//tasks.withType<Test> {
//    useJUnitPlatform()
//}