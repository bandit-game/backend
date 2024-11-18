package be.kdg.integration5.gameplatformcontext;

import org.springframework.boot.SpringApplication;

public class TestGamePlatformContextApplication {

    public static void main(String[] args) {
        SpringApplication.from(GamePlatformContextApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
