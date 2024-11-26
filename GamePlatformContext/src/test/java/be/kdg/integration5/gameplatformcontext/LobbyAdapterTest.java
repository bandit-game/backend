package be.kdg.integration5.gameplatformcontext;


import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby.LobbyDatabasedAdapter;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby.LobbyJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.gameplatformcontext.domain.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = { GamePlatformContextApplication.class })
@SpringBootTest
public class LobbyAdapterTest {

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private LobbyDatabasedAdapter lobbyDatabasedAdapter;

    private Game game;

    private Player player;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;


    @BeforeEach
    void setUp() {
        game = new Game(
                "Title",
                "http://localhost:8080/image",
                "http://localhost:8080/backend",
                "http://localhost:8080/frontend",
                new ArrayList<>(),
                new Price(100.0, Currency.getInstance("USD")),
                2,
                "Description"

        );
        GameJpaEntity gameJpa = GameJpaEntity.of(game);
        player = new Player(
                new PlayerId(UUID.randomUUID()),
                "user",
                18,
                Player.Gender.MALE

        );
        playerJpaRepository.save(PlayerJpaEntity.of(player));
        gameJpaRepository.save(gameJpa);
    }

    @AfterEach
    void tearDown() {
        lobbyJpaRepository.deleteAll();
        playerJpaRepository.deleteById(player.getPlayerId().uuid());
        gameJpaRepository.deleteById(game.getGameId().uuid());
    }

    @Test
    @Transactional
    void findAllNotFilledNonPrivateLobbiesByGameIdShouldReturnNoLobbies() {
        // Arrange
        // Act
        List<Lobby> lobbies = lobbyDatabasedAdapter.findAllNotFilledNonPrivateLobbiesByGameId(game.getGameId(), false);

        // Assert
        assertEquals(0, lobbies.size());
    }

    @Test
    @Transactional
    void findAllNotFilledNonPrivateLobbiesByGameIdShouldReturnLobbies() {
        // Arrange
        Lobby lobby = new Lobby(false, game, player);
        lobbyDatabasedAdapter.save(lobby);
        // Act
        List<Lobby> lobbies = lobbyDatabasedAdapter.findAllNotFilledNonPrivateLobbiesByGameId(game.getGameId(), false);
        // Assert
        assertEquals(1, lobbies.size());
        assertEquals(lobbies.getFirst().getLobbyOwner().getPlayerId(), player.getPlayerId());
    }

    @Test
    void testMethod() {
        System.out.println("Hellow");
    }
}
