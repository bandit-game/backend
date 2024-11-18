package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.port.in.GetAllGamesUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindGamePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllGamesUseCaseImpl implements GetAllGamesUseCase {
    private final FindGamePort findGamePort;

    @Autowired
    public GetAllGamesUseCaseImpl(FindGamePort findGamePort) {
        this.findGamePort = findGamePort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return findGamePort.findAllGames();
    }
}
