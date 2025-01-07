package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.*;
import be.kdg.integration5.checkerscontext.port.in.MovePieceCommand;
import be.kdg.integration5.checkerscontext.port.in.MovePieceUseCase;
import be.kdg.integration5.checkerscontext.port.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovePieceUseCaseImpl implements MovePieceUseCase {
    private final FindGamePort findGamePort;
    private final PersistGamePort persistGamePort;
    private final NotifyPlayerPort notifyPlayerPort;
    private final NotifyGameEndPort notifyGameEndPort;
    private final NotifyCheckersMoveMadePort notifyCheckersMoveMadePort;

    public MovePieceUseCaseImpl(FindGamePort findGamePort, PersistGamePort persistGamePort, NotifyPlayerPort notifyPlayerPort, NotifyGameEndPort notifyGameEndPort, NotifyCheckersMoveMadePort notifyCheckersMoveMadePort) {
        this.findGamePort = findGamePort;
        this.persistGamePort = persistGamePort;
        this.notifyPlayerPort = notifyPlayerPort;
        this.notifyGameEndPort = notifyGameEndPort;
        this.notifyCheckersMoveMadePort = notifyCheckersMoveMadePort;
    }

    @Override
    public void movePiece(MovePieceCommand movePieceCommand) {
        GameId gameId = movePieceCommand.gameId();
        Game game = findGamePort.findById(gameId);

        Game updatedGame = game;
        if (!game.isFinished()) {
            PlayerId moverId = movePieceCommand.playerId();
            Move move = movePieceCommand.move();

            game.getBoard().movePiece(moverId, move);
            updatedGame = persistGamePort.update(game);

            notifyCheckersMoveMadePort.notifyCheckersMoveMade(new CheckersMoveMadeCommand(gameId, moverId, move));
            if (updatedGame.checkForGameOver())
                notifyGameEndPort.notifyGameEnd(game);
        }
        notifyPlayerPort.notifyAllPlayersWithGameState(updatedGame);
    }
}
