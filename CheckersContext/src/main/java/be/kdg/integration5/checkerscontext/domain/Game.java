package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Game {
    private GameId playedMatchId;
    private boolean isFinished;
    private boolean isDraw;
    private Player winner;

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

    public Game(GameId playedMatchId, Board board, boolean isFinished, boolean isDraw, Player winner, List<Player> players) {
        this.playedMatchId = playedMatchId;
        this.board = board;
        this.isFinished = isFinished;
        this.isDraw = isDraw;
        this.winner = winner;
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

    public boolean checkForGameOver() {
        Optional<Player> winningPiecesOwner = board.getWinningPiecesOwner();
        if (winningPiecesOwner.isPresent()) {
            this.winner = winningPiecesOwner.get();
            this.isFinished = true;
            return isFinished;
        }

        if (bothPlayersWantDraw()) {
            this.isDraw = true;
            this.isFinished = true;
        }
        // Some additional logic can be added here

        return isFinished;
    }



    private boolean bothPlayersWantDraw() {
        return players.stream().allMatch(Player::doesWantToDraw);
    }


}
