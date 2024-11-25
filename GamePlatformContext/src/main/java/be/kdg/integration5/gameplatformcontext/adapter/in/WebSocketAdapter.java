package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.LobbyId;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.out.NotifyLobbyUpdatePort;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAdapter implements NotifyLobbyUpdatePort {
    @Override
    public void notifyPlayerJoinedLobby(LobbyId lobbyId, PlayerId playerId) {

    }

    @Override
    public void notifyLobbyIsFull(Lobby lobby) {

    }
}
