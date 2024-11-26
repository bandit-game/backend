package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

public interface FindPlayerPort {
    Player findPlayerById(PlayerId playerId);
}
