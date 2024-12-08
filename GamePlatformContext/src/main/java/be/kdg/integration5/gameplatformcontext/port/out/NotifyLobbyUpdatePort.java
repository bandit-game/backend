package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.LobbyId;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

public interface NotifyLobbyUpdatePort {
    void notifyAllPlayersInLobby(Lobby lobby);
    void sendGameRedirectionLink(Lobby lobby, Game game);
}
