package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
//@ToString
public class Game {
    private GameId playedMatchId;
    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;

    private Board board;
    private List<Player> players;

    public Game(GameId playedMatchId, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.players = players;
    }

    public void start() {
        this.startedTime = LocalDateTime.now();
        this.board = new Board(this, getFirstPlayer());
    }

    private Player getFirstPlayer() {
        return players.stream().filter(Player::isFirst).findFirst().orElseThrow(
                () -> new IllegalStateException("Player not found")
        );
    }
}
