package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.common.events.statistics.NewPlayerRegisteredEvent;
import be.kdg.integration5.statisticscontext.domain.Location;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;
import be.kdg.integration5.statisticscontext.port.in.RegisterNewPlayerUseCase;
import be.kdg.integration5.statisticscontext.port.out.PersistPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegisterNewPlayerUseCaseImpl implements RegisterNewPlayerUseCase {

    private final PersistPlayerPort persistPlayerPort;

    public RegisterNewPlayerUseCaseImpl(PersistPlayerPort persistPlayerPort) {
        this.persistPlayerPort = persistPlayerPort;
    }

    @Override
    public Player register(NewPlayerRegisteredEvent event) {
        Player player = new Player(
                event.playerName(),
                new PlayerId(event.playerId()),
                event.age(),
                Player.Gender.valueOf(event.gender()),
                new Location(event.country(), event.city()));
        return persistPlayerPort.save(player);
    }
}
