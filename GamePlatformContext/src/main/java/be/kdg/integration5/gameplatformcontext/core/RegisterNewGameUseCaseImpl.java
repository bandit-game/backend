package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.common.events.GameAddedEvent;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.Price;
import be.kdg.integration5.gameplatformcontext.port.in.RegisterNewGameUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.PersistGamePort;
import be.kdg.integration5.gameplatformcontext.port.out.SendGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@Transactional
public class RegisterNewGameUseCaseImpl implements RegisterNewGameUseCase {

    private final PersistGamePort persistGamePort;
    private final SendGamePort sendGamePort;

    public RegisterNewGameUseCaseImpl(PersistGamePort persistGamePort, SendGamePort sendGamePort) {
        this.persistGamePort = persistGamePort;
        this.sendGamePort = sendGamePort;
    }

    @Override
    public Game register(GameAddedEvent event) {
        Game game = new Game(
                event.gameName(),
                event.gameImageUrl(),
                event.backendApiUrl(),
                event.frontendUrl(),
                new Price(event.price(), Currency.getInstance(event.currency())),
                event.maxLobbyPlayersAmount(),
                event.description()
        );
        Game savedGame = persistGamePort.save(game);
        sendGamePort.sendGame(savedGame);
        return savedGame;
    }
}
