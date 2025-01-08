package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.AchievementJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.AchievementJpaRepository;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game.GameDatabaseAdapter;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game.GameJpaRepository;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.move.MoveJpaRepository;
import be.kdg.integration5.checkersachievementcontext.domain.Player;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.Achievement;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.AchievementsProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class PlayerDatabaseAdapterTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private PlayerDatabaseAdapter playerDatabaseAdapter;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private AchievementJpaRepository achievementJpaRepository;

    private List<Player> players;
    private Set<Achievement> allAchievements = AchievementsProvider.ACHIEVEMENTS_SET;


    @DynamicPropertySource
    static void configureDataSourceProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @BeforeEach
    void setUp() {
        Player player1 = new Player(new PlayerId(UUID.randomUUID()), allAchievements);
        Player player2 = new Player(new PlayerId(UUID.randomUUID()), allAchievements);
        players = new ArrayList<>(List.of(player1, player2));
    }

    @AfterEach
    void tearDown() {
        achievementJpaRepository.deleteAll();
        playerJpaRepository.deleteAll();
    }

    @Test
    void findById() {
        playerDatabaseAdapter.saveAll(players);

        Player player = playerDatabaseAdapter.findById(players.get(0).getPlayerId());

        assertNotNull(player);
        assertNotNull(player.getAchievements());
        assertEquals(allAchievements.size(), player.getAchievements().size());
    }

    @Test
    void saveAll() {
        playerDatabaseAdapter.saveAll(players);

        List<AchievementJpaEntity> allSavedAchievements = achievementJpaRepository.findAll();
        List<PlayerJpaEntity> allSavedPlayers = playerJpaRepository.findAll();

        assertEquals(players.size(), allSavedPlayers.size());
        assertEquals(this.allAchievements.size()*2, allSavedAchievements.size());
    }
}