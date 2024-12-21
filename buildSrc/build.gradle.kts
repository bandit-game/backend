plugins {
    id("groovy-gradle-plugin")
    id ("java-gradle-plugin")
}


group = "be.kdg.integration5"
version = "1.0.0"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.5")
}

gradlePlugin {
    plugins {
        create("moduleConfig") {
            id = "module-config"
            implementationClass = "ModuleConfigCustom"
        }
    }
}