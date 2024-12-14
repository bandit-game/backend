package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.domain.PlayerId;

import java.util.List;

public interface NotifyPlayerPort {
    void notifyPossibleMovesForPlayer(List<Move> moves, Player player);
    void notifyPlayerOfGameState(PlayerId playerId, Game game);
    void notifyAllPlayersWithGameState(Game game);
}
