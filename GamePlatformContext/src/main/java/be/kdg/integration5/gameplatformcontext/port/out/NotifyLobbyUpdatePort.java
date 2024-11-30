package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.LobbyId;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

public interface NotifyLobbyUpdatePort {
    void notifyPlayerJoinedLobby(Lobby lobby, PlayerId playerId);
    void notifyLobbyIsFull(Lobby lobby);
    void notifyAllPlayersInLobby(Lobby lobby);
}
