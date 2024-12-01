package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.JoinLobbyRequestDTO;
import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LeaveLobbyRequestDTO;
import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LobbyDTO;
import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LobbyReadinessDTO;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.LobbyId;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchCommand;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchUseCase;
import be.kdg.integration5.gameplatformcontext.port.in.LeaveTheLobbyUseCase;
import be.kdg.integration5.gameplatformcontext.port.in.LobbyPlayersReadinessUseCase;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyWebSocketController {
    private final FindQuickMatchUseCase findQuickMatchUseCase;
    private final LobbyPlayersReadinessUseCase lobbyPlayersReadinessUseCase;

    public LobbyWebSocketController(FindQuickMatchUseCase findQuickMatchUseCase, LobbyPlayersReadinessUseCase lobbyPlayersReadinessUseCase) {
        this.findQuickMatchUseCase = findQuickMatchUseCase;
        this.lobbyPlayersReadinessUseCase = lobbyPlayersReadinessUseCase;
    }


    @MessageMapping("/join-lobby")
    public void joinLobby(@Payload JoinLobbyRequestDTO joinLobbyRequestDTO) {
        FindQuickMatchCommand command = new FindQuickMatchCommand(
                new PlayerId(joinLobbyRequestDTO.playerId()),
                new GameId(joinLobbyRequestDTO.gameId())
        );

        findQuickMatchUseCase.findQuickMatch(command);

    }


    @MessageMapping("/ready-to-play")
    public void setLobbyReadiness(@Payload LobbyReadinessDTO lobbyReadiness) {
        lobbyPlayersReadinessUseCase.getGameUrlForPlayers(new LobbyId(lobbyReadiness.lobbyId()));
    }

}
