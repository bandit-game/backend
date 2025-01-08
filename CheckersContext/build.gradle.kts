plugins {
	id("module-config")
	id("com.adarshr.test-logger") version "4.0.0"
}

dependencies {
	implementation(project(":common"))
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-security")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")

	// Test Dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:rabbitmq")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
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
	include("**/unit/**")
}

// Integration Tests
val integrationTest by tasks.registering(Test::class) {
	description = "Runs integration tests."
	group = "verification"
	useJUnitPlatform()
	include("**/integration/**")
}

// End-to-End Tests
val e2eTest by tasks.registering(Test::class) {
	description = "Runs end-to-end tests."
	group = "verification"
	useJUnitPlatform()
	include("**/e2e/**")
}

// Add all tasks to check lifecycle
tasks.named("check") {
	dependsOn(unitTest, integrationTest, e2eTest)
}