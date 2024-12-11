package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.MovePieceCommand;
import be.kdg.integration5.checkerscontext.port.in.MovePieceUseCase;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.NotifyPlayerPort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.springframework.stereotype.Service;

@Service
public class MovePieceUseCaseImpl implements MovePieceUseCase {
    private final FindGamePort findGamePort;
    private final PersistGamePort persistGamePort;
    private final NotifyPlayerPort notifyPlayerPort;

    public MovePieceUseCaseImpl(FindGamePort findGamePort, PersistGamePort persistGamePort, NotifyPlayerPort notifyPlayerPort) {
        this.findGamePort = findGamePort;
        this.persistGamePort = persistGamePort;
        this.notifyPlayerPort = notifyPlayerPort;
    }

    @Override
    public Game movePiece(MovePieceCommand movePieceCommand) {
        GameId gameId = movePieceCommand.gameId();
        Game game = findGamePort.findById(gameId);

        PlayerId moverId = movePieceCommand.playerId();
        int currentX = movePieceCommand.currentX();
        int currentY = movePieceCommand.currentY();
        int targetX = movePieceCommand.targetX();
        int targetY = movePieceCommand.targetY();

        game.getBoard().movePiece(moverId, currentX, currentY, targetX, targetY);
        persistGamePort.save(game);

        notifyPlayerPort.notifyAllPlayersWithGameState(game);

        return game;
    }
}
