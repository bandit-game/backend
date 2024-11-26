package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;

import java.util.List;

public interface FindGamePort {
    List<Game> findAllGames();
    Game findGameById(GameId gameId);
}
