package be.kdg.integration5.gameplatformcontext.adapter.in;

import be.kdg.integration5.gameplatformcontext.adapter.in.dto.JoinLobbyRequestDTO;
import be.kdg.integration5.gameplatformcontext.adapter.in.dto.LobbyDTO;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchCommand;
import be.kdg.integration5.gameplatformcontext.port.in.FindQuickMatchUseCase;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyWebSocketController {
    private final FindQuickMatchUseCase findQuickMatchUseCase;

    public LobbyWebSocketController(FindQuickMatchUseCase findQuickMatchUseCase) {
        this.findQuickMatchUseCase = findQuickMatchUseCase;
    }


    @MessageMapping("/join-lobby")
    public void joinLobby(@Payload JoinLobbyRequestDTO joinLobbyRequestDTO) {

        FindQuickMatchCommand command = new FindQuickMatchCommand(
                new PlayerId(joinLobbyRequestDTO.playerId()),
                new GameId(joinLobbyRequestDTO.gameId())
        );

        findQuickMatchUseCase.findQuickMatch(command);

    }

}
