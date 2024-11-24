plugins {
    id("module-config")
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0") // or latest version
    compileOnly("org.projectlombok:lombok")

}