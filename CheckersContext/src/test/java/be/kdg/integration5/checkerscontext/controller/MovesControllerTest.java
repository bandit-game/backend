package be.kdg.integration5.checkerscontext.controller;

import be.kdg.integration5.checkerscontext.adapter.in.MovesController;
import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.port.out.DeleteGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MovesControllerTest {
    private UUID createdGameId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovesController movesController;

    @Autowired
    private PersistGamePort persistGamePort;

    @Autowired
    private DeleteGamePort deleteGamePort;

    @BeforeEach
    void setUp() {
        createdGameId = UUID.randomUUID();
        //TODO
        // Implement domain model

        List<Player> players = new ArrayList<>(List.of(
                new Player("Player 1", false),
                new Player("Player 2", true)
        ));
        Game game = new Game(new GameId(createdGameId), players);
        persistGamePort.save(game);
    }

    @Test
    void whenRequestedListOfValidMovesForSpecificPiecesShouldBeReturned() throws Exception {
        //TODO
        mockMvc.perform(get("").param("position", "1")).andExpect(status().isOk());

    }

    @AfterEach
    void tearDown() {
        deleteGamePort.deleteById(new GameId(createdGameId));
    }
}