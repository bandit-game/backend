package be.kdg.integration5.statisticscontext.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private GameId gameId;
    private String name;

    public Game(GameId gameId, String name) {
        this.gameId = gameId;
        this.name = name.toLowerCase().replace(" ", "_");
    }
}
