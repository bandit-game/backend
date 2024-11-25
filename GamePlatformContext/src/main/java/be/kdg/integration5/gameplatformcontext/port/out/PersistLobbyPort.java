package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;

public interface PersistLobbyPort {
    Lobby save(Lobby lobby);
}
