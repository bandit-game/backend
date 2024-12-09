package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;

public interface GetGameByIdUseCase {
    Game getGameById(GameId gameId);
}
