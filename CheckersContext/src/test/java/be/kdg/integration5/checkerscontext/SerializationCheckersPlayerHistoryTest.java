package be.kdg.integration5.checkerscontext;

import be.kdg.integration5.common.config.CustomPlayerMatchHistoryObjectMapper;
import be.kdg.integration5.common.domain.PlayerMatchHistory;
import be.kdg.integration5.common.events.FinishGameSessionEvent;
import be.kdg.integration5.common.events.extraPlayerHistory.CheckersPlayerHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationCheckersPlayerHistoryTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = CustomPlayerMatchHistoryObjectMapper.get();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    void testSerializeExtraHistory() throws Exception {
        PlayerMatchHistory history = new CheckersPlayerHistory(10, 2, 3);
        String json = objectMapper.writeValueAsString(history);
        assertNotNull(json);
        System.out.println(json);
    }


    @Test
    void testShouldSerializeAndDeserializeCheckerPlayerHistory() throws Exception {
        PlayerMatchHistory player1CheckersHistory = new CheckersPlayerHistory(10, 2,3);
        PlayerMatchHistory player2CheckersHistory = new CheckersPlayerHistory(11, 4,4);

        FinishGameSessionEvent.PlayerResult player1 = new FinishGameSessionEvent.PlayerResult(
                UUID.randomUUID(),
                25,
                "male",
                new FinishGameSessionEvent.Location("New York", "USA"),
                20,
                false,
                player1CheckersHistory
        );

        FinishGameSessionEvent.PlayerResult player2 = new FinishGameSessionEvent.PlayerResult(
                UUID.randomUUID(),
                22,
                "female",
                new FinishGameSessionEvent.Location("Los Angeles", "USA"),
                18,
                true,
                player2CheckersHistory
        );

        FinishGameSessionEvent.SessionLength sessionLength = new FinishGameSessionEvent.SessionLength(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        FinishGameSessionEvent event = new FinishGameSessionEvent(
                UUID.randomUUID(),
                "checkers",
                false,
                sessionLength,
                List.of(player1, player2)
        );

        String json = objectMapper.writeValueAsString(event);
        FinishGameSessionEvent deserializedEvent = objectMapper.readValue(json, FinishGameSessionEvent.class);

        // Verify that the deserialized event is not null
        assertNotNull(deserializedEvent, "Deserialized event should not be null");

        // Check if the game type is "checkers"
        assertEquals(event.gameType(), deserializedEvent.gameType(), "Game types should match");

        // Check if the draw flag is correctly deserialized
        assertEquals(event.draw(), deserializedEvent.draw(), "Draw flag should match");

        // Check if session length is correctly deserialized
        assertEquals(event.sessionLength().start(), deserializedEvent.sessionLength().start(), "Session start times should match");
        assertEquals(event.sessionLength().end(), deserializedEvent.sessionLength().end(), "Session end times should match");

        // Verify PlayerResults were correctly deserialized
        FinishGameSessionEvent.PlayerResult deserializedPlayer1 = deserializedEvent.playersResults().get(0);
        FinishGameSessionEvent.PlayerResult deserializedPlayer2 = deserializedEvent.playersResults().get(1);

        // Verify that PlayerExtraHistory was properly deserialized as CheckersPlayerHistory for both players
        assertInstanceOf(CheckersPlayerHistory.class, deserializedPlayer1.extraHistory(), "Player 1's extra history should be of type CheckersPlayerHistory");
        assertInstanceOf(CheckersPlayerHistory.class, deserializedPlayer2.extraHistory(), "Player 2's extra history should be of type CheckersPlayerHistory");


    }
}
