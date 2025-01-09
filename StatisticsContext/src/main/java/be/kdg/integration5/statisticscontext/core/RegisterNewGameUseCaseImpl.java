package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.statistics.NewGameRegisteredEvent;
import be.kdg.integration5.statisticscontext.domain.Game;
import be.kdg.integration5.statisticscontext.domain.GameId;
import be.kdg.integration5.statisticscontext.port.in.RegisterNewGameUseCase;
import be.kdg.integration5.statisticscontext.port.out.PersistGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegisterNewGameUseCaseImpl implements RegisterNewGameUseCase {

    private final PersistGamePort persistGamePort;

    public RegisterNewGameUseCaseImpl(PersistGamePort persistGamePort) {
        this.persistGamePort = persistGamePort;
    }

    @Override
    public Game register(NewGameRegisteredEvent event) {
        Game game = new Game(new GameId(event.gameId()), event.gameName());
        return persistGamePort.save(game);
    }
}
