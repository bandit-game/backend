package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.*;
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
    public void movePiece(MovePieceCommand movePieceCommand) {
        GameId gameId = movePieceCommand.gameId();
        Game game = findGamePort.findById(gameId);

        PlayerId moverId = movePieceCommand.playerId();
        Move move = movePieceCommand.move();

        game.getBoard().movePiece(moverId, move);

        persistGamePort.save(game);
        notifyPlayerPort.notifyAllPlayersWithGameState(game);
    }
}
