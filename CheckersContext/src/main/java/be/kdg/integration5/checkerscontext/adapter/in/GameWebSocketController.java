package be.kdg.integration5.checkerscontext.adapter.in;


import be.kdg.integration5.checkerscontext.adapter.in.dto.GetGameStateRequestDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.GetMovesRequestDto;
import be.kdg.integration5.checkerscontext.adapter.in.dto.PieceMovementRequestDto;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {

    private final FindAllPossibleMovesUseCase findAllPossibleMovesUseCase;
    private final GetGameStateUseCase getGameStateUseCase;
    private final MovePieceUseCase movePieceUseCase;

    public GameWebSocketController(FindAllPossibleMovesUseCase findAllPossibleMovesUseCase, GetGameStateUseCase getGameStateUseCase, MovePieceUseCase movePieceUseCase) {
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
        MovePieceCommand movePieceCommand = new MovePieceCommand(
                gameId,
                playerId,
                pieceMovementRequestDto.currentX(),
                pieceMovementRequestDto.currentY(),
                pieceMovementRequestDto.targetX(),
                pieceMovementRequestDto.targetY()
        );
        movePieceUseCase.movePiece(movePieceCommand);
    }
}
