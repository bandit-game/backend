package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.common.events.GameAddedEvent;
import be.kdg.integration5.gameplatformcontext.domain.Game;

public interface RegisterNewGameUseCase {
    Game register(GameAddedEvent event);
}
