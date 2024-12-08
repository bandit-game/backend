package be.kdg.integration5.checkerscontext.adapter.in;


import be.kdg.integration5.checkerscontext.adapter.in.dto.GetGameStateRequestDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.GetMovesRequestDto;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import be.kdg.integration5.checkerscontext.port.in.GetStateOfGameUseCase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {

    private final FindAllPossibleMovesUseCase findAllPossibleMovesUseCase;
    private final GetStateOfGameUseCase getStateOfGameUseCase;

    public GameWebSocketController(FindAllPossibleMovesUseCase findAllPossibleMovesUseCase, GetStateOfGameUseCase getStateOfGameUseCase) {
        this.findAllPossibleMovesUseCase = findAllPossibleMovesUseCase;
        this.getStateOfGameUseCase = getStateOfGameUseCase;
    }

    @MessageMapping("/get-moves")
    public void getPossibleMovesForSquare(@Payload GetMovesRequestDto requestDTO) {
        FindAllPossibleMovesCommand command  = new FindAllPossibleMovesCommand(
          new GameId(requestDTO.gameId()),
                requestDTO.x(),
                requestDTO.y()
        );

        findAllPossibleMovesUseCase.findAllPossibleMoves(command);
    }

    @MessageMapping("/get-state")
    public void getGameStateForPlayer(@Payload GetGameStateRequestDto dto) {
        PlayerId playerId = new PlayerId(dto.playerId());
        getStateOfGameUseCase.sendStateTo(playerId);
    }
}
