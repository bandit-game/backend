package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LobbyDTO;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.out.NotifyLobbyUpdatePort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class LobbyMessagingAdapter implements NotifyLobbyUpdatePort {

    private final SimpMessagingTemplate messagingTemplate;

    public LobbyMessagingAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyPlayerJoinedLobby(Lobby lobby, PlayerId playerId) {
        LobbyDTO lobbyDTO = LobbyDTO.of(lobby);
        this.sendLobbyStateToPlayer(playerId, lobbyDTO);

    }

    @Override
    public void notifyLobbyIsFull(Lobby lobby) {

    }

    @Override
    public void notifyAllPlayersInLobby(Lobby lobby) {
        LobbyDTO lobbyDTO = LobbyDTO.of(lobby);
        lobby.getPlayers()
                .forEach(player -> this.sendLobbyStateToPlayer(player.getPlayerId(), lobbyDTO));
    }

    private void sendLobbyStateToPlayer(PlayerId playerId, LobbyDTO lobbyDTO) {
        messagingTemplate.convertAndSend("/queue/user/" + playerId.uuid().toString(), lobbyDTO);
    }
}

