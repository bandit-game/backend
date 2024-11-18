package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;

import java.util.List;

public interface FindGamePort {
    List<Game> findAllGames();
}
