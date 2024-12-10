package be.kdg.integration5.checkerscontext.domain;

import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Game {
    private GameId playedMatchId;
    private boolean isFinished;

    private Board board;
    private List<Player> players;
    private Player currentPlayer;

    public Game(GameId playedMatchId, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.players = players;
    }

    public Game(GameId playedMatchId, Board board, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.board = board;
        this.players = players;
    }

    {
        this.currentPlayer = getFirstPlayer();
    }

    public void start() {
        this.board = new Board();
        board.setUpNewBoard(getFirstPlayer(), getSecondPlayer());
    }

    private Player getFirstPlayer() {
        return players.getFirst();
    }

    private Player getSecondPlayer() {
        return players.getLast();
    }
}
