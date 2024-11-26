package be.kdg.integration5.gameplatformcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    private static final int MAX_PLAYERS = 2;

    private GameId gameId;
    private String title;
    private String description;
    private Price price;
    private int maxLobbyPlayersAmount;
    private List<Achievement> achievements;

    public Game(String title, String description, Price price, List<Achievement> achievements) {
        this.gameId = new GameId(UUID.randomUUID());
        this.title = title;
        this.description = description;
        this.price = price;
        this.achievements = achievements;
        this.maxLobbyPlayersAmount = MAX_PLAYERS;
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }
}
