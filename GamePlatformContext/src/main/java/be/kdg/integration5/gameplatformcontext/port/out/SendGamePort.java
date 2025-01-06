package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;

public interface SendGamePort {
    void sendGame(Game game);
}
