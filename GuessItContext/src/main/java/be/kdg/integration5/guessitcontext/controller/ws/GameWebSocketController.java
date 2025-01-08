package be.kdg.integration5.guessitcontext.controller.ws;

import be.kdg.integration5.guessitcontext.domain.PlayerId;
import be.kdg.integration5.guessitcontext.domain.SessionId;
import be.kdg.integration5.guessitcontext.service.GameService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {

    private final GameService gameService;

    public GameWebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/guess")
    public void getPossibleMovesForSquare(@Payload PlayerMoveDto playerMoveDto) {
        gameService.makeMove(new PlayerId(playerMoveDto.playerId()), playerMoveDto.guessNumber());
    }

    @MessageMapping("/state")
    public void getInitialState(@Payload SessionRequestDto sessionRequestDto) {
        gameService.sendSessionInfo(new SessionId(sessionRequestDto.sessionId()));
    }
}
