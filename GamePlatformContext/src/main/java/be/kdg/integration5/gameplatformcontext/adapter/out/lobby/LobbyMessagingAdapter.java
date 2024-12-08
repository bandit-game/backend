package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.GameRedirectionDTO;
import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LobbyDTO;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.Player;
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
    public void notifyAllPlayersInLobby(Lobby lobby) {
        LobbyDTO lobbyDTO = LobbyDTO.of(lobby);
        lobby.getPlayers().forEach(
                player -> messagingTemplate.convertAndSend(getUserQueue(player), lobbyDTO));
    }

    @Override
    public void sendGameRedirectionLink(Lobby lobby, Game game) {
        GameRedirectionDTO gameRedirectionDTO = new GameRedirectionDTO(game.getFrontendUrl());
        lobby.getPlayers()
                .forEach(player -> messagingTemplate.convertAndSend(getUserQueue(player), gameRedirectionDTO));
    }

    private String getUserQueue(Player player) {
        return "/queue/user/" + player.getPlayerId().uuid().toString();
    }
}

