package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;

public interface NotifyGameStartPort {
    void notifyGameStart(Lobby lobby);
}
