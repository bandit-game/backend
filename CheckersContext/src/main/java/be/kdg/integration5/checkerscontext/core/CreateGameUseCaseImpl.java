package be.kdg.integration5.checkerscontext.core;

import be.kdg.integration5.checkerscontext.domain.Game;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Player;
import be.kdg.integration5.checkerscontext.domain.PlayerId;
import be.kdg.integration5.checkerscontext.port.in.CreateGameCommand;
import be.kdg.integration5.checkerscontext.port.in.CreateGameUseCase;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistPlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CreateGameUseCaseImpl implements CreateGameUseCase {

    private final PersistGamePort persistGamePort;
    private final PersistPlayerPort persistPlayerPort;

    public CreateGameUseCaseImpl(PersistGamePort persistGamePort, PersistPlayerPort persistPlayerPort) {
        this.persistGamePort = persistGamePort;
        this.persistPlayerPort = persistPlayerPort;
    }

    @Override
    public void initiate(CreateGameCommand command) {
        List<Player> players = command.playersJoined().stream().map(
                pj -> new Player(new PlayerId(pj.playerId()), pj.playerName())
        ).toList();
        players = persistPlayerPort.saveAll(players);
        Game game = new Game(new GameId(command.lobbyId()), players);
        game.start();
        persistGamePort.save(game);

    }
}
