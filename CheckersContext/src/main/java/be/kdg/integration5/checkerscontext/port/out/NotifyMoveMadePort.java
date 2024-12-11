package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.PlayerId;

public interface NotifyMoveMadePort {
    void notifyMoveMade(GameId gameId, PlayerId playerId, Move move);
}
