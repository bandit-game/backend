package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.LobbyId;

public interface LobbyPlayersReadinessUseCase {
    void getGameUrlForPlayers(LobbyId lobbyId);
}
