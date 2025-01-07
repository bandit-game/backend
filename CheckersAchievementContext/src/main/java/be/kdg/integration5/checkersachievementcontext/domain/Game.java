package be.kdg.integration5.checkersachievementcontext.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Game {
    private GameId gameId;
    private List<Player> players;

    private Board board;

    public Game(GameId gameId, List<Player> players, Board board) {
        this.gameId = gameId;
        this.players = new ArrayList<>(players);
        this.board = board;
    }
}
