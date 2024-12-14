package be.kdg.integration5.checkerscontext.adapter.in;


import be.kdg.integration5.checkerscontext.adapter.in.dto.GetGameStateRequestDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.GetMovesRequestDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.PieceMovementRequestDto;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Move;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.domain.exception.MoveNotValidException;
import be.kdg.integration5.checkerscontext.port.in.*;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    private final FindAllPossibleMovesUseCase findAllPossibleMovesUseCase;
    private final GetGameStateUseCase getGameStateUseCase;
    private final MovePieceUseCase movePieceUseCase;

    public GameWebSocketController(SimpMessagingTemplate messagingTemplate, FindAllPossibleMovesUseCase findAllPossibleMovesUseCase, GetGameStateUseCase getGameStateUseCase, MovePieceUseCase movePieceUseCase) {
        this.messagingTemplate = messagingTemplate;
        this.findAllPossibleMovesUseCase = findAllPossibleMovesUseCase;
        this.getGameStateUseCase = getGameStateUseCase;
        this.movePieceUseCase = movePieceUseCase;
    }

    @MessageMapping("/get-moves")
    public void getPossibleMovesForSquare(@Payload GetMovesRequestDto getMovesRequestDto) {
        FindAllPossibleMovesCommand command  = new FindAllPossibleMovesCommand(
          new GameId(getMovesRequestDto.gameId()),
                getMovesRequestDto.x(),
                getMovesRequestDto.y()
        );

        findAllPossibleMovesUseCase.findAllPossibleMoves(command);
    }

    @MessageMapping("/get-state")
    public void getGameStateForPlayer(@Payload GetGameStateRequestDto getGameStateRequestDto) {
        GameId gameId = new GameId(getGameStateRequestDto.gameId());
        PlayerId playerId = new PlayerId(getGameStateRequestDto.playerId());
        SendGameStateToPlayerCommand sendGameStateToPlayerCommand = new SendGameStateToPlayerCommand(gameId, playerId);
        getGameStateUseCase.sendGameStateToPlayer(sendGameStateToPlayerCommand);
    }

    @MessageMapping("/make-move")
    public void movePiece(@Payload PieceMovementRequestDto pieceMovementRequestDto) {
        GameId gameId = new GameId(pieceMovementRequestDto.gameId());
        PlayerId playerId = new PlayerId(pieceMovementRequestDto.playerId());
        Move move = pieceMovementRequestDto.move().toDomain();

        MovePieceCommand movePieceCommand = new MovePieceCommand(gameId, playerId, move);
        movePieceUseCase.movePiece(movePieceCommand);
    }

    @MessageExceptionHandler
    public void handleMoveNotValidException(MoveNotValidException moveNotValidException) {
        messagingTemplate.convertAndSend(
                "/queue/user/" + moveNotValidException.getPlayerId().uuid().toString(),
                moveNotValidException.getMessage()
        );
    }
}
