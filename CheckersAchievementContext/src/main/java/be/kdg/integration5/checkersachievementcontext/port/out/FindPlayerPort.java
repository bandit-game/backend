package be.kdg.integration5.checkersachievementcontext.port.out;

import be.kdg.integration5.checkersachievementcontext.domain.Player;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;

public interface FindPlayerPort {
    Player findById(PlayerId playerId);
}
