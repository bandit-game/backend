package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

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

    public Game(GameId playedMatchId, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.players = players;
    }

    public Game(GameId playedMatchId, Board board, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.board = board;
        this.players = players;
    }


    public void start() {
        this.board = new Board(getPlayers());
        board.setUpNewBoard(getFirstPlayer());
    }

    private Player getFirstPlayer() {
        return players.getFirst();
    }

    private Player getSecondPlayer() {
        return players.getLast();
    }

    public boolean playerIsParticipant(PlayerId playerId) {
        return players.stream().anyMatch(player -> player.getPlayerId().equals(playerId));
    }
}
