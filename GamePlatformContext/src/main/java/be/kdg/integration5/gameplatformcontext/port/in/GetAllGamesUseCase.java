package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Game;

import java.util.List;

public interface GetAllGamesUseCase {
    List<Game> getAllGames();
}
