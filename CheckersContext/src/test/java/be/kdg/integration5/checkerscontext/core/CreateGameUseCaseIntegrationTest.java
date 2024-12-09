package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.port.in.CreateGameCommand;
import be.kdg.integration5.checkerscontext.port.in.CreateGameUseCase;
import be.kdg.integration5.common.events.LobbyCreatedEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class CreateGameUseCaseIntegrationTest {
    private static final String LOBBY_QUEUE = "lobby_queue";
    private static RabbitMQContainer rabbitMQContainer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CreateGameUseCase createGameUseCase; // Mocked in your test configuration

    @BeforeAll
    static void startRabbitMQ() {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management")
                .withExposedPorts(5672, 15672);
        rabbitMQContainer.start();
        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getMappedPort(5672).toString());
    }

    @AfterAll
    static void stopRabbitMQ() {
        rabbitMQContainer.stop();
    }

    @Test
    public void testCreateGameFromLobbyListener() {
        // Arrange: Create a mock LobbyCreatedEvent
        UUID lobbyId = UUID.randomUUID();
        UUID firstPlayerId = UUID.randomUUID();
        List<LobbyCreatedEvent.PlayerEvent> playerEvents = List.of(
                new LobbyCreatedEvent.PlayerEvent(UUID.randomUUID(), "Alice"),
                new LobbyCreatedEvent.PlayerEvent(firstPlayerId, "Bob")
        );

        LobbyCreatedEvent testEvent = new LobbyCreatedEvent(
                lobbyId,
                firstPlayerId,
                playerEvents
        );

        // Publish the test event to the RabbitMQ queue
        rabbitTemplate.convertAndSend(LOBBY_QUEUE, testEvent);

        // Act: Capture the argument passed to createGameUseCase.initiate
        ArgumentCaptor<CreateGameCommand> captor = ArgumentCaptor.forClass(CreateGameCommand.class);
        verify(createGameUseCase, timeout(5000)).initiate(captor.capture()); // Wait up to 5 seconds for the call

        // Assert: Verify the captured command
        CreateGameCommand capturedCommand = captor.getValue();
        assertThat(capturedCommand.lobbyId()).isEqualTo(lobbyId);
        assertThat(capturedCommand.playersJoined()).hasSize(2);
        assertThat(capturedCommand.playersJoined().get(1).isFirst()).isTrue(); // Bob is the first player

    }
}
