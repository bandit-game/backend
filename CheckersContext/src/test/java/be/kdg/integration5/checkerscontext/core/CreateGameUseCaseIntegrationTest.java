package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.port.in.CreateGameUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.common.events.LobbyCreatedEvent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class CreateGameUseCaseIntegrationTest {
    private static final String LOBBY_QUEUE = "lobby_queue";
    private static RabbitMQContainer rabbitMQContainer;
    private static CachingConnectionFactory connectionFactory;

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    private FindGamePort findGamePort;

    @BeforeAll
    static void startRabbitMQ() {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management")
                .withExposedPorts(5672, 15672)
                .withEnv("RABBITMQ_DEFAULT_USER", "guest")
                .withEnv("RABBITMQ_DEFAULT_PASS", "guest")
                .waitingFor(Wait.forLogMessage(".*Server startup complete.*\\n", 1));

        rabbitMQContainer.start();

        connectionFactory = new CachingConnectionFactory(
                rabbitMQContainer.getHost(), rabbitMQContainer.getMappedPort(5672));
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // Configure RabbitTemplate
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // Declare the queue
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue(LOBBY_QUEUE, true));
    }


    @AfterAll
    static void stopRabbitMQ() {
        if (connectionFactory != null) {
            connectionFactory.destroy();
        }
        if (rabbitMQContainer != null) {
            rabbitMQContainer.stop();
        }
    }

    @Test
    @Transactional
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
        Game game = findGamePort.findById(new GameId(lobbyId));
        assertThat(game).isNotNull();
        assertThat(game.getPlayers()).hasSize(2);
    }
}
