package be.kdg.integration5.checkerscontext.adapter.in;


import be.kdg.integration5.checkerscontext.adapter.in.dto.GetMovesRequestDTO;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesCommand;
import be.kdg.integration5.checkerscontext.port.in.FindAllPossibleMovesUseCase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {

    private final FindAllPossibleMovesUseCase findAllPossibleMovesUseCase;

    public GameWebSocketController(FindAllPossibleMovesUseCase findAllPossibleMovesUseCase) {
        this.findAllPossibleMovesUseCase = findAllPossibleMovesUseCase;
    }

    @MessageMapping("/get-moves")
    public void getPossibleMovesForSquare(@Payload GetMovesRequestDTO requestDTO) {
        FindAllPossibleMovesCommand command  = new FindAllPossibleMovesCommand(
          new GameId(requestDTO.gameId()),
                requestDTO.x(),
                requestDTO.y()
        );

        findAllPossibleMovesUseCase.findAllPossibleMoves(command);
    }
}
