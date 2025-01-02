package core;


import be.kdg.integration5.common.events.PlayerMoveEvent;
import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.statisticscontext.StatisticsContextApplication;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.statisticscontext.domain.*;
import be.kdg.integration5.statisticscontext.port.in.GameSessionStartedUseCase;
import be.kdg.integration5.statisticscontext.port.in.PlayerMadeMoveUseCase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@ContextConfiguration(classes = { StatisticsContextApplication.class })
@SpringBootTest
public class PlayerMoveIntegrationTest {

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private MoveJpaRepository moveJpaRepository;

    @Autowired
    private PlayerJpaConverter playerJpaConverter;

    @Autowired
    private PlayerMadeMoveUseCase playerMadeMoveUseCase;

    @Autowired
    private GameSessionStartedUseCase gameSessionStartedUseCase;

    private List<Player> playerList;

    private Session session;

    @BeforeEach
    void setup() {
        // Seed test data for game and players
        Game chessGame = new Game(new GameId(UUID.randomUUID()), "chess");
        gameJpaRepository.save(new GameJpaEntity(chessGame.gameId().uuid(), chessGame.name()));

        Player player1 = new Player("Player 1", new PlayerId(UUID.randomUUID()), 25, Player.Gender.MALE, new Location("USA", "New York"));
        Player player2 = new Player("Player 2", new PlayerId(UUID.randomUUID()), 30, Player.Gender.FEMALE, new Location("Canada", "Toronto"));

        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        List<PlayerJpaEntity> playerEntities = playerList.stream().map(playerJpaConverter::toJpa).toList();
        playerJpaRepository.saveAll(playerEntities);

        StartGameSessionEvent event = new StartGameSessionEvent(
                chessGame.name(),
                UUID.randomUUID(),
                player1.getPlayerId().uuid(),
                playerList.stream().map(p -> p.getPlayerId().uuid()).toList(),
                LocalDateTime.now()

        );
        session = gameSessionStartedUseCase.startGame(event);

    }

    @Test
    void testPlayerMovedSuccessfully() {
        // Arrange
        PlayerMoveEvent event = new PlayerMoveEvent(
                session.getSessionId().uuid(),
                playerList.get(0).getPlayerId().uuid(),
                playerList.get(1).getPlayerId().uuid(),
                LocalDateTime.now().plusMinutes(1)
        );


        // Act
        playerMadeMoveUseCase.saveMove(event);

        // Assert
        List<MoveJpaEntity> moveEntities = moveJpaRepository.findAll();

        assertThat(moveEntities, hasSize(2));
        assertThat(moveEntities, hasItem(hasProperty("endTime", nullValue())));
        assertThat(moveEntities, everyItem(hasProperty("moveNumber", equalTo(1))));
        assertThat(moveEntities, hasItem(hasProperty("startTime", equalTo(event.timestamp()))));
    }
}
