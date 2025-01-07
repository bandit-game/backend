package be.kdg.integration5.checkersachievementcontext.core;

import be.kdg.integration5.checkersachievementcontext.domain.*;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleCheckersMoveMadeCommand;
import be.kdg.integration5.checkersachievementcontext.port.in.HandleCheckersMoveMadeUseCase;
import be.kdg.integration5.checkersachievementcontext.port.out.FindGamePort;
import be.kdg.integration5.checkersachievementcontext.port.out.PersistGamePort;
import be.kdg.integration5.checkersachievementcontext.port.out.PersistPlayerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandleCheckersMoveMadeUseCaseImpl implements HandleCheckersMoveMadeUseCase {
    private final FindGamePort findGamePort;
    private final PersistGamePort persistGamePort;
    private final PersistPlayerPort persistPlayerPort;

    @Autowired
    public HandleCheckersMoveMadeUseCaseImpl(FindGamePort findGamePort, PersistGamePort persistGamePort, PersistPlayerPort persistPlayerPort) {
        this.findGamePort = findGamePort;
        this.persistGamePort = persistGamePort;
        this.persistPlayerPort = persistPlayerPort;
    }

    @Override
    public void handleCheckersMoveMade(HandleCheckersMoveMadeCommand handleCheckersMoveMadeCommand) {
        GameId gameId = handleCheckersMoveMadeCommand.gameId();
        Game game = findGamePort.findById(gameId);
        Board board = game.getBoard();

        Move madeMove = new Move(
                handleCheckersMoveMadeCommand.moverId(),
                handleCheckersMoveMadeCommand.oldPosition(),
                handleCheckersMoveMadeCommand.newPosition(),
                handleCheckersMoveMadeCommand.madeAt()
        );

        board.addMove(madeMove);

        List<Player> players = game.getPlayers();

        for (Player player : players)
            player.checkAchievementsFulfilled(game);

        persistPlayerPort.updateAll(players);
        persistGamePort.update(game);
    }
}
