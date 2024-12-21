/**
 @author Yevhen Zinenko
 */
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.JavaVersion
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class ModuleConfigCustom implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply('java')
        project.plugins.apply('org.springframework.boot')
        project.plugins.apply('io.spring.dependency-management')

        // Java version compatibility
        project.tasks.withType(JavaCompile).configureEach { compileTask ->
            compileTask.sourceCompatibility = JavaVersion.VERSION_21
            compileTask.targetCompatibility = JavaVersion.VERSION_21
        }

        // Repositories
        project.repositories {
            mavenCentral()
        }

        // Test configuration
        project.tasks.withType(Test).tap {
            configureEach {
                useJUnitPlatform()
            }
        }

        // Example dependencies
        // project.dependencies {
        //     testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
        //     testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
        // }
    }
}
