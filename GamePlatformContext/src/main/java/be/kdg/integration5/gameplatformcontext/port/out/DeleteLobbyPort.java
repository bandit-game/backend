package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;

public interface DeleteLobbyPort {
    void deleteLobby(Lobby lobby);
}
