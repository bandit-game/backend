package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;

import java.time.LocalDateTime;

public interface FindGamePort {
    Game findById(GameId gameId);
    Game findGameByPlayerAndGameEndNull(PlayerId playerId);
}
