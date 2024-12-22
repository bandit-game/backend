plugins {
    id("groovy-gradle-plugin")
}


group = "be.kdg.integration5"
version = "0.0.1-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.5")
}
