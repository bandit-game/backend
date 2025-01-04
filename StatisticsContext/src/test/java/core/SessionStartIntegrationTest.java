package core;

import be.kdg.integration5.common.events.StartGameSessionEvent;
import be.kdg.integration5.statisticscontext.StatisticsContextApplication;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.move.MoveJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player_metrics.PlayerMetricsJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.player_metrics.PlayerMetricsJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_metrics.PlayerMetricsJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionId;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaRepository;
import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaConverter;
import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaRepository;
import be.kdg.integration5.statisticscontext.domain.*;
import be.kdg.integration5.statisticscontext.port.in.GameSessionStartedUseCase;
import be.kdg.integration5.statisticscontext.port.out.FindGamePort;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import be.kdg.integration5.statisticscontext.port.out.PersistSessionPort;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ContextConfiguration(classes = { StatisticsContextApplication.class })
@SpringBootTest
public class SessionStartIntegrationTest {

    @Autowired
    private GameSessionStartedUseCase gameSessionStartedUseCase;

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private SessionJpaRepository sessionJpaRepository;

    @Autowired
    private PlayerSessionJpaRepository playerSessionJpaRepository;

    @Autowired
    private PlayerMetricsJpaRepository playerMetricsJpaRepository;

    @Autowired
    private PlayerJpaConverter playerJpaConverter;

    @Autowired
    private PlayerMetricsJpaConverter playerMetricsJpaConverter;

    @Autowired
    private MoveJpaRepository moveJpaRepository;

    private List<Player> playerList;


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
        List<PlayerJpaEntity> playerJpaEntities = playerList.stream().map(playerJpaConverter::toJpa).toList();
        List<PlayerMetricsJpaEntity> playerMetricsJpaEntities = playerJpaEntities.stream().map(p -> {
            PlayerMetricsJpaEntity playerMetricsJpaEntity = new PlayerMetricsJpaEntity();
            playerMetricsJpaConverter.updateFields(playerMetricsJpaEntity, new Metrics());
            playerMetricsJpaEntity.setPlayerId(p.getPlayerId());
            playerMetricsJpaEntity.setPlayer(p);
            return playerMetricsJpaEntity;
        }).toList();

        playerJpaRepository.saveAll(playerJpaEntities);
        playerMetricsJpaRepository.saveAll(playerMetricsJpaEntities);
    }

    @AfterEach
    void tearDown() {
        playerList = new ArrayList<>();
        moveJpaRepository.deleteAll();
        playerSessionJpaRepository.deleteAll();
        sessionJpaRepository.deleteAll();
        gameJpaRepository.deleteAll();
        playerMetricsJpaRepository.deleteAll();
        playerJpaRepository.deleteAll();
    }

    @Test
    void testStartGameSession() {
        // Arrange
        UUID lobbyId = UUID.randomUUID();
        UUID playerId1 = playerList.get(0).getPlayerId().uuid();
        UUID playerId2 = playerList.get(1).getPlayerId().uuid();
        LocalDateTime timestamp = LocalDateTime.now();

        StartGameSessionEvent event = new StartGameSessionEvent(
                "Chess",
                lobbyId,
                playerId1,
                List.of(playerId1, playerId2),
                timestamp
        );
        List<PlayerSessionId> playerSessionIds = List.of(new PlayerSessionId(lobbyId, playerId1), new PlayerSessionId(lobbyId, playerId2));
                // Act
        gameSessionStartedUseCase.startGame(event);

        // Assert

        List<PlayerSessionJpaEntity> playerSessionJpaEntities = playerSessionJpaRepository.findAllById(playerSessionIds);
        List<MoveJpaEntity> moveJpaEntities = moveJpaRepository.findAll();
        List<PlayerMetricsJpaEntity> playerMetricsJpaEntities = playerMetricsJpaRepository.findAll();
        Optional<SessionJpaEntity> savedSession = sessionJpaRepository.findById(lobbyId);

        assertThat(savedSession.isPresent(), is(true));
        SessionJpaEntity session = savedSession.get();

        assertThat(session.getSessionId(), is(equalTo(lobbyId)));
        assertThat(session.getStartTime(), is(notNullValue()));
        assertThat(playerSessionJpaEntities.size(), is(2));
        assertThat(moveJpaEntities.size(), is(1));
        assertThat(session.getWinner(), is(nullValue()));
        assertThat(playerMetricsJpaEntities.size(), is(2));

    }

}
