package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.port.in.GetGameByIdUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindGamePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetGameByIdUseCaseImpl implements GetGameByIdUseCase {

    private final FindGamePort findGamePort;

    public GetGameByIdUseCaseImpl(FindGamePort findGamePort) {
        this.findGamePort = findGamePort;
    }

    @Override
    public Game getGameById(GameId gameId) {
        return findGamePort.findGameById(gameId);
    }
}
